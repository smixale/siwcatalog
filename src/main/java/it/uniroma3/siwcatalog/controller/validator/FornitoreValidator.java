package it.uniroma3.siwcatalog.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import it.uniroma3.siwcatalog.model.Fornitore;
import it.uniroma3.siwcatalog.model.Prodotto;
import it.uniroma3.siwcatalog.repository.FornitoreRepository;

@Component
public class FornitoreValidator implements Validator{
    
    @Autowired
    private FornitoreRepository fornitoreRepository;

    @Override
	public boolean supports(Class<?> aClass) {
		return Prodotto.class.equals(aClass);
	}

    @Override
    public void validate(Object target, Errors errors) {
        Fornitore fornitore = (Fornitore) target;
        if (fornitore.getNomeFornitore()!=null && fornitore.getEmailFornitore()!=null && 
        this.fornitoreRepository.existsByNomeFornitoreAndEmailFornitore(fornitore.getNomeFornitore(), fornitore.getEmailFornitore())) {
            errors.reject("fornitore.duplicato");
        }
    }
}
