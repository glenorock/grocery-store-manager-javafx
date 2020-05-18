/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alimentation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhy
 */
@Entity
@Table(name = "fournisseur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fournisseur.findAll", query = "SELECT f FROM Fournisseur f")
    , @NamedQuery(name = "Fournisseur.findByCodeFour", query = "SELECT f FROM Fournisseur f WHERE f.codeFour = :codeFour")
    , @NamedQuery(name = "Fournisseur.findByNom", query = "SELECT f FROM Fournisseur f WHERE f.nom = :nom")
    , @NamedQuery(name = "Fournisseur.findByAdresse", query = "SELECT f FROM Fournisseur f WHERE f.adresse = :adresse")})
public class Fournisseur implements Serializable ,EntityClasses {

    @OneToMany(mappedBy = "codeFour")
    private Collection<Produit> produitCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codeFour")
    private Integer codeFour;
    @Column(name = "nom")
    private String nom;
    @Column(name = "Adresse")
    private String adresse;

    public Fournisseur() {
    }

    public Fournisseur(Integer codeFour) {
        this.codeFour = codeFour;
    }

    public Integer getCodeFour() {
        return codeFour;
    }

    public void setCodeFour(Integer codeFour) {
        this.codeFour = codeFour;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codeFour != null ? codeFour.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        Fournisseur other = (Fournisseur) object;
        return this.codeFour.intValue() == other.codeFour.intValue();
    }

    @Override
    public String toString() {
        return this.getNom();
    }

    @Override
    public Integer getId() {
        return this.codeFour;
    }

    public Fournisseur(String nom, String adresse) {
        this.codeFour = null;
        this.nom = nom;
        this.adresse = adresse;
    }

    public Fournisseur(String nom) {
        this.codeFour = null;
        this.nom = nom;
        this.adresse = null;
    }

    public Fournisseur(Integer codeFour, String nom, String adresse) {
        this.codeFour = codeFour;
        this.nom = nom;
        this.adresse = adresse;
    }
    
    public static List<Fournisseur>  getFournisseurs(){
         EntityManager em = emf.createEntityManager();
         TypedQuery<Fournisseur> query = em.createQuery("select f from Fournisseur f",Fournisseur.class);
         return query.getResultList();
    }

    @XmlTransient
    public Collection<Produit> getProduitCollection() {
        return produitCollection;
    }

    public void setProduitCollection(Collection<Produit> produitCollection) {
        this.produitCollection = produitCollection;
    }
    
     
}