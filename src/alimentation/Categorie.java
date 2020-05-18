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
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Query;
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhy
 */
@Entity
@Table(name = "categorie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Categorie.findAll", query = "SELECT c FROM Categorie c")
    , @NamedQuery(name = "Categorie.findByIdCat", query = "SELECT c FROM Categorie c WHERE c.idCat = :idCat")
    , @NamedQuery(name = "Categorie.findByNomCat", query = "SELECT c FROM Categorie c WHERE c.nomCat = :nomCat")})
public class Categorie implements Serializable ,EntityClasses {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCat")
    private Integer idCat;
    @Basic(optional = false)
    @Column(name = "nomCat")
    private String nomCat;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCategorie")
    private Collection<Produit> produitCollection;

    public Categorie() {
    }

    public Categorie(Integer idCat) {
        this.idCat = idCat;
    }

    public Categorie(Integer idCat, String nomCat) {
        this.idCat = idCat;
        this.nomCat = nomCat;
    }

    public Integer getIdCat() {
        return idCat;
    }

    public void setIdCat(Integer idCat) {
        this.idCat = idCat;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    @XmlTransient
    public Collection<Produit> getProduitCollection() {
        return produitCollection;
    }

    public void setProduitCollection(Collection<Produit> produitCollection) {
        this.produitCollection = produitCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCat != null ? idCat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        Categorie other = (Categorie) object;
        return this.idCat.intValue()==other.idCat.intValue();
    }

    @Override
    public String toString() {
        return this.nomCat;
    }

    @Override
    public Integer getId() {
        return this.idCat;
    }
    public static List<Categorie> getCategories(){
        //Categories dans la base de donnees
        EntityManager em = emf.createEntityManager();
        TypedQuery<Categorie> query = em.createQuery("Select c from Categorie c order by c.nomCat",Categorie.class);
        return query.getResultList();
    }
    public static List<String> getCategoriesNames(){
        //Noms des categories dans la base de donnees
        EntityManager em = emf.createEntityManager();
        TypedQuery<String> query = em.createQuery("Select c.nomCat from Categorie c",String.class);
        return query.getResultList();
    }
    public static Integer getCategorieIdByName(String name){
        //Id d'un categorie par son nom
        EntityManager em = emf.createEntityManager();
        Query query = em.createQuery("Select c.idCat from Categorie c where c.nomCat = :nom").setParameter("nom", name);
        return (Integer)query.getSingleResult();
    }
    public static Categorie getCategorieByName(String name){
        //Categorie dont le nom est "name"
        EntityManager em = emf.createEntityManager();
        TypedQuery<Categorie> query = em.createQuery("Select c from Categorie c where c.nomCat = :nom",Categorie.class).setParameter("nom", name);
        return query.getSingleResult();
    }
    
    public Categorie(String nomCat){
        this.idCat = null;
        this.nomCat = nomCat;
    }
    
}
