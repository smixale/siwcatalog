package it.uniroma3.siwcatalog.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Prodotto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank
    private String nomeProdotto;

    @NotNull
    private Float prezzo;

    private String descrizione;

    @ManyToMany
    private Set <Fornitore> fornitori;

    @OneToOne(cascade = CascadeType.REMOVE)
    private Immagine immagineProdotto;

    @OneToMany(cascade = CascadeType.REMOVE)
    private List <Commento> commenti;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeProdotto() {
        return nomeProdotto;
    }

    public void setNomeProdotto(String nomeProdotto) {
        this.nomeProdotto = nomeProdotto;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public Set<Fornitore> getFornitori() {
        return fornitori;
    }

    public void setFornitori(Set<Fornitore> fornitori) {
        this.fornitori = fornitori;
    }

    public Immagine getImmagineProdotto() {
        return immagineProdotto;
    }

    public void setImmagineProdotto(Immagine immagineProdotto) {
        this.immagineProdotto = immagineProdotto;
    }

    public List<Commento> getCommenti() {
        return commenti;
    }

    public void setCommenti(List<Commento> commenti) {
        this.commenti = commenti;
    }

    public Float getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(Float prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((prezzo == null) ? 0 : prezzo.hashCode());
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
        Prodotto other = (Prodotto) obj;
        if (prezzo == null) {
            if (other.prezzo != null)
                return false;
        } else if (!prezzo.equals(other.prezzo))
            return false;
        return true;
    }

}
