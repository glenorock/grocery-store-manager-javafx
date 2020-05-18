
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alimentation;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
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
import javax.persistence.Table;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhy
 */
@Entity
@Table(name = "gestionnaire")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gestionnaire.findAll", query = "SELECT g FROM Gestionnaire g")
    , @NamedQuery(name = "Gestionnaire.findByIdGest", query = "SELECT g FROM Gestionnaire g WHERE g.idGest = :idGest")
    , @NamedQuery(name = "Gestionnaire.findByNomGest", query = "SELECT g FROM Gestionnaire g WHERE g.nomGest = :nomGest")
    , @NamedQuery(name = "Gestionnaire.findByTypeGest", query = "SELECT g FROM Gestionnaire g WHERE g.typeGest = :typeGest")
    , @NamedQuery(name = "Gestionnaire.findByLogin", query = "SELECT g FROM Gestionnaire g WHERE g.login = :login")
    , @NamedQuery(name = "Gestionnaire.findByPwd", query = "SELECT g FROM Gestionnaire g WHERE g.pwd = :pwd")
    , @NamedQuery(name = "Gestionnaire.findByActif", query = "SELECT g FROM Gestionnaire g WHERE g.actif = :actif")})
public class Gestionnaire implements Serializable ,EntityClasses{

    @Basic(optional = false)
    @Column(name = "typeGest")
    private String typeGest;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGest")
    private Integer idGest;
    @Basic(optional = false)
    @Column(name = "nomGest")
    private String nomGest;
    @Basic(optional = false)
    @Column(name = "login")
    private String login;
    @Basic(optional = false)
    @Column(name = "pwd")
    private String pwd;
    @Basic(optional = false)
    @Column(name = "actif")
    private boolean actif;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCaissiere")
    private Collection<Facture> factureCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGest")
    private Collection<Gestionstock> gestionstockCollection;

    public Gestionnaire() {
    }

    public Gestionnaire(Integer idGest) {
        this.idGest = idGest;
    }

    public Gestionnaire(Integer idGest, String nomGest, String typeGest, String login, String pwd, boolean actif) {
        this.idGest = idGest;
        this.nomGest = nomGest;
        this.typeGest = typeGest;
        this.login = login;
        this.pwd = pwd;
        this.actif = actif;
    }

    public Integer getIdGest() {
        return idGest;
    }

    public void setIdGest(Integer idGest) {
        this.idGest = idGest;
    }

    public String getNomGest() {
        return nomGest;
    }

    public void setNomGest(String nomGest) {
        this.nomGest = nomGest;
    }

    public String getTypeGest() {
        return typeGest;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    @XmlTransient
    public Collection<Facture> getFactureCollection() {
        return factureCollection;
    }

    public void setFactureCollection(Collection<Facture> factureCollection) {
        this.factureCollection = factureCollection;
    }

    @XmlTransient
    public Collection<Gestionstock> getGestionstockCollection() {
        return gestionstockCollection;
    }

    public void setGestionstockCollection(Collection<Gestionstock> gestionstockCollection) {
        this.gestionstockCollection = gestionstockCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGest != null ? idGest.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gestionnaire)) {
            return false;
        }
        Gestionnaire other = (Gestionnaire) object;
        if ((this.idGest == null && other.idGest != null) || (this.idGest != null && !this.idGest.equals(other.idGest))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nomGest;
    }

    @Override
    public Integer getId() {
        return this.idGest;
    }
    
    public static List<Gestionnaire> getGestionnaires(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Gestionnaire> query = em.createQuery("Select c from Gestionnaire c",Gestionnaire.class);
        return query.getResultList();
    }
    
    public static Gestionnaire getGestionnaireByUserName(String name){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Gestionnaire> query = em.createQuery("Select c from Gestionnaire c where c.login = :name",Gestionnaire.class).setParameter("name",name);
        return query.getSingleResult();
    }
    
    public static Gestionnaire getGestionnaireByName(String name){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Gestionnaire> query = em.createQuery("Select c from Gestionnaire c where c.nomGest = :name",Gestionnaire.class).setParameter("name",name);
        return query.getSingleResult();
    }
    
    public boolean checkPassword(String pwd){
        return this.pwd == null ? pwd == null : this.pwd.equals(pwd);
    }

    public Gestionnaire(String nomGest, String typeGest, String login, String pwd, boolean actif, Collection<Facture> factureCollection, Collection<Gestionstock> gestionstockCollection) {
        this.idGest =null;
        this.nomGest = nomGest;
        this.typeGest = typeGest;
        this.login = login;
        this.pwd = pwd;
        this.actif = actif;
        this.factureCollection = factureCollection;
        this.gestionstockCollection = gestionstockCollection;
    }

    public Gestionnaire(String nomGest,String login, String pwd, String typeGest) {
        this.idGest = null;
        this.nomGest = nomGest;
        this.typeGest = typeGest;
        this.login = login;
        this.pwd = pwd;
        this.actif = true;
        this.factureCollection = null;
        this.gestionstockCollection = null;
    }
    public Gestionnaire(String nomGest,String login, String pwd, String typeGest,boolean actif) {
        this.idGest = null;
        this.nomGest = nomGest;
        this.typeGest = typeGest;
        this.login = login;
        this.pwd = pwd;
        this.actif = actif;
        this.factureCollection = null;
        this.gestionstockCollection = null;
    }
    
    public boolean isActif() {
        return actif;
    }

    
    public void activer(){
        if(!isActif()){
            EntityManager em = emf.createEntityManager();
            Gestionnaire gest = em.find(Gestionnaire.class,this.idGest);
            em.getTransaction().begin();
            gest.setActif(true);
            em.getTransaction().commit();
            em.close();
        }
    }
    
    public void toggleActivate(){
        EntityManager em = emf.createEntityManager();
            Gestionnaire gest = em.find(Gestionnaire.class,this.idGest);
            em.getTransaction().begin();
            gest.setActif(!gest.isActif());
            em.getTransaction().commit();
            em.close();
    }
    
    public String getType(){
        return this.typeGest;
    }
    
    public void changePassword(String pwd){
        EntityManager em = emf.createEntityManager();
        Gestionnaire gest = em.find(Gestionnaire.class,this.idGest);
        em.getTransaction().begin();
        gest.setPwd(pwd);
        em.getTransaction().commit();
        em.close();
    }
    
    public void changeUserName(String nom){
        EntityManager em = emf.createEntityManager();
        Gestionnaire gest = em.find(Gestionnaire.class,this.idGest);
        em.getTransaction().begin();
        gest.setLogin(nom);
        em.getTransaction().commit();
        em.close();
    }

    

    public void setTypeGest(String typeGest) {
        this.typeGest = typeGest;
    }
    
}
