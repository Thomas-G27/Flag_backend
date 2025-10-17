package com.takima.backskeleton.services;

import com.takima.backskeleton.DAO.PartieDao;
import com.takima.backskeleton.DAO.PaysDao;
import com.takima.backskeleton.DAO.QuestionDao;
import com.takima.backskeleton.DTO.*;
import com.takima.backskeleton.models.Partie;
import com.takima.backskeleton.models.Pays;
import com.takima.backskeleton.models.Question;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PartieService {

    private final PartieDao partieDao;
    private final QuestionDao questionDao;
    private final PaysDao paysDao;

    @Transactional
    public PartieSummaryDto startPartie(PartieStartDto dto) {
        Partie partie = new Partie();
        partie.setDate(dto.getDate());

        // Génère la banque de pays pour les questions
        List<Pays> pool = (dto.getCountryIds() != null && !dto.getCountryIds().isEmpty())
                ? paysDao.findAllById(dto.getCountryIds())
                : paysDao.findAll();
        Collections.shuffle(pool);

        int n = Math.min(dto.getNbQuestions() != null ? dto.getNbQuestions() : 10, pool.size());
        List<Question> questions = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Pays target = pool.get(i);
            questions.add(buildQuestion(partie, i + 1, target, pool));
        }
        partie.setQuestions(questions);
        updateScore(partie); // "0/N"

        Partie saved = partieDao.save(partie);
        return toSummary(saved);
    }

    private Question buildQuestion(Partie partie, int ordre, Pays target, List<Pays> pool) {
        // Exemple : QCM "Quel est le pays correspondant à ... ?" -> 1 bonne réponse + 3 leurres
        Set<String> options = new LinkedHashSet<>();
        options.add(target.getName());

        // ajoute 3 distracteurs uniques
        int idx = 0;
        while (options.size() < 4 && idx < pool.size()) {
            String candidate = pool.get(idx).getName();
            if (!candidate.equalsIgnoreCase(target.getName())) options.add(candidate);
            idx++;
        }
        // si pool trop petit, complète "N/A"
        while (options.size() < 4) options.add("N/A");

        List<String> shuffled = new ArrayList<>(options);
        Collections.shuffle(shuffled);

        Question q = new Question();
        q.setPartie(partie);
        q.setOrdre(ordre);
        q.setPays(target);
        q.setEnonce("Quel est le pays correct ?");
        q.setOptionA(shuffled.get(0));
        q.setOptionB(shuffled.get(1));
        q.setOptionC(shuffled.get(2));
        q.setOptionD(shuffled.get(3));
        q.setCorrectOption(indexToOption(shuffled.indexOf(target.getName())));
        q.setIsCorrect(null);
        return q;
    }

    private Question.Option indexToOption(int i) {
        return switch (i) {
            case 0 -> Question.Option.A;
            case 1 -> Question.Option.B;
            case 2 -> Question.Option.C;
            case 3 -> Question.Option.D;
            default -> Question.Option.A;
        };
    }

    @Transactional()
    public QuestionDto nextQuestion(Long partieId) {
        Partie partie = partieDao.findOneWithQuestions(partieId)
                .orElseThrow(() -> new NoSuchElementException("Partie introuvable"));

        return partie.getQuestions().stream()
                .filter(q -> q.getUserOption() == null)   // non répondue
                .sorted(Comparator.comparing(Question::getOrdre))
                .findFirst()
                .map(this::toQuestionDto)
                .orElse(null); // plus de questions
    }

    @Transactional
    public QuestionResultDto answer(Long partieId, Long questionId, ReponseDto answer) {
        Partie partie = partieDao.findOneWithQuestions(partieId)
                .orElseThrow(() -> new NoSuchElementException("Partie introuvable"));
        Question q = partie.getQuestions().stream()
                .filter(qq -> qq.getId().equals(questionId))
                .findFirst().orElseThrow(() -> new NoSuchElementException("Question introuvable"));

        Question.Option chosen = Question.Option.valueOf(answer.getOption().toUpperCase());
        q.setUserOption(chosen);
        q.setIsCorrect(chosen == q.getCorrectOption());
        questionDao.save(q);

        updateScore(partie);
        partieDao.save(partie);

        return toResultDto(q);
    }

    @Transactional
    public PartieSummaryDto getSummary(Long partieId) {
        Partie partie = partieDao.findOneWithQuestions(partieId)
                .orElseThrow(() -> new NoSuchElementException("Partie introuvable"));
        return toSummary(partie);
    }

    private void updateScore(Partie partie) {
        int total = partie.getQuestions().size();
        int correct = (int) partie.getQuestions().stream()
                .filter(q -> Boolean.TRUE.equals(q.getIsCorrect()))
                .count();
        partie.setScore(correct + "/" + total);
    }

    /* ---- Mapping ---- */

    private QuestionDto toQuestionDto(Question q) {
        QuestionDto dto = new QuestionDto();
        dto.setId(q.getId());
        dto.setOrdre(q.getOrdre());
        dto.setEnonce(q.getEnonce());
        dto.setOptionA(q.getOptionA());
        dto.setOptionB(q.getOptionB());
        dto.setOptionC(q.getOptionC());
        dto.setOptionD(q.getOptionD());
        return dto;
    }

    private QuestionResultDto toResultDto(Question q) {
        QuestionResultDto dto = new QuestionResultDto();
        dto.setId(q.getId());
        dto.setOrdre(q.getOrdre());
        dto.setEnonce(q.getEnonce());
        dto.setOptionA(q.getOptionA());
        dto.setOptionB(q.getOptionB());
        dto.setOptionC(q.getOptionC());
        dto.setOptionD(q.getOptionD());
        dto.setUserOption(q.getUserOption() != null ? q.getUserOption().name() : null);
        dto.setIsCorrect(q.getIsCorrect());
        return dto;
    }

    private PartieSummaryDto toSummary(Partie p) {
        PartieSummaryDto dto = new PartieSummaryDto();
        dto.setId(p.getId());
        dto.setScore(p.getScore());
        dto.setQuestions(
                p.getQuestions().stream()
                        .sorted(Comparator.comparing(Question::getOrdre))
                        .map(this::toResultDto)
                        .toList()
        );
        return dto;
    }
}
