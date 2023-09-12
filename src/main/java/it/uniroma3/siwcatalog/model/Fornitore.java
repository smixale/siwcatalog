package it.uniroma3.siwcatalog.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.validation.constraints.NotBlank;

import java.util.Set;

import jakarta.persistence.*;

@Entity
public class Fornitore {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nomeFornitore;

    @NotBlank
    private String emailFornitore;

    @ManyToMany(mappedBy = "fornitori")
    private Set <Prodotto> prodottiForniti;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeFornitore() {
        return nomeFornitore;
    }

    public void setNomeFornitore(String nomeFornitore) {
        this.nomeFornitore = nomeFornitore;
    }

    public String getEmailFornitore() {
        return emailFornitore;
    }

    public void setEmailFornitore(String emailFornitore) {
        this.emailFornitore = emailFornitore;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((nomeFornitore == null) ? 0 : nomeFornitore.hashCode());
        result = prime * result + ((emailFornitore == null) ? 0 : emailFornitore.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Fornitore other = (Fornitore) obj;
        if (nomeFornitore == null) {
            if (other.nomeFornitore != null)
                return false;
        } else if (!nomeFornitore.equals(other.nomeFornitore))
            return false;
        if (emailFornitore == null) {
            if (other.emailFornitore != null)
                return false;
        } else if (!emailFornitore.equals(other.emailFornitore))
            return false;
        return true;
    }

    public Set<Prodotto> getProdottiForniti() {
        return prodottiForniti;
    }

    public void setProdottiForniti(Set<Prodotto> prodottiForniti) {
        this.prodottiForniti = prodottiForniti;
    }

    
}
