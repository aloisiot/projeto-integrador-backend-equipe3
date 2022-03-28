package dh.projetointegradorctd.backend.service;

import dh.projetointegradorctd.backend.exception.global.ResorceNotFoundException;
import dh.projetointegradorctd.backend.model.storage.Evaluation;
import dh.projetointegradorctd.backend.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationService extends TemplateCrudService<Evaluation> {

    private final EvaluationRepository repository;

    @Autowired
    public EvaluationService(EvaluationRepository repository) {
        super(repository);
        this.repository = (EvaluationRepository) super.repository;
    }

    public List<Evaluation> findAllByProductId(Long productId) {
        List<Evaluation> evaluations = repository.findByProductId(productId);
        if (evaluations.isEmpty()) {
            throw new ResorceNotFoundException("Não hã avaliações para esse produto");
        }
        return evaluations;
    }
}
