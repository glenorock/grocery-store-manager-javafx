/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alimentation;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jhy
 */
@Entity
@Table(name = "produit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Produit.findAll", query = "SELECT p FROM Produit p")
    , @NamedQuery(name = "Produit.findByCodePro", query = "SELECT p FROM Produit p WHERE p.codePro = :codePro")
    , @NamedQuery(name = "Produit.findByNomPro", query = "SELECT p FROM Produit p WHERE p.nomPro = :nomPro")
    , @NamedQuery(name = "Produit.findByPrixVente", query = "SELECT p FROM Produit p WHERE p.prixVente = :prixVente")
    , @NamedQuery(name = "Produit.findByPrixAchat", query = "SELECT p FROM Produit p WHERE p.prixAchat = :prixAchat")
    , @NamedQuery(name = "Produit.findByQte", query = "SELECT p FROM Produit p WHERE p.qte = :qte")
    , @NamedQuery(name = "Produit.findByDescription", query = "SELECT p FROM Produit p WHERE p.description = :description")
    , @NamedQuery(name = "Produit.findByCodeFour", query = "SELECT p FROM Produit p WHERE p.codeFour = :codeFour")
    , @NamedQuery(name = "Produit.findByDateInsertion", query = "SELECT p FROM Produit p WHERE p.dateInsertion = :dateInsertion")
    , @NamedQuery(name = "Produit.findByActif", query = "SELECT p FROM Produit p WHERE p.actif = :actif")})
public class Produit implements Serializable ,EntityClasses{

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "qte")
    private BigDecimal qte;

    @Column(name = "wholeOnly")
    private Boolean wholeOnly;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "codePro")
    private Integer codePro;
    @Basic(optional = false)
    @Column(name = "nomPro")
    private String nomPro;
    @Column(name = "prixVente")
    private Integer prixVente;
    @Basic(optional = false)
    @Column(name = "prixAchat")
    private int prixAchat;
    @Basic(optional = false)
    @Column(name = "description")
    private String description;
    @JoinColumn(name = "codeFour", referencedColumnName = "codeFour")
    @ManyToOne(optional = false)
    private Fournisseur codeFour;
    @Column(name = "dateInsertion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateInsertion;
    @Basic(optional = false)
    @Column(name = "actif")
    private boolean actif;
    @JoinColumn(name = "idCategorie", referencedColumnName = "idCat")
    @ManyToOne(optional = false)
    private Categorie idCategorie;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codePro")
    private Collection<Gestionstock> gestionstockCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codePro")
    private Collection<Lignefacture> lignefactureCollection;

    public Produit() {
    }

    public Produit(Integer codePro) {
        this.codePro = codePro;
    }

    public Integer getCodePro() {
        return codePro;
    }

    public void setCodePro(Integer codePro) {
        this.codePro = codePro;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public Integer getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(Integer prixVente) {
        this.prixVente = prixVente;
    }

    public int getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(int prixAchat) {
        this.prixAchat = prixAchat;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Fournisseur getCodeFour() {
        return codeFour;
    }

    public void setCodeFour(Fournisseur codeFour) {
        this.codeFour = codeFour;
    }

    public Date getDateInsertion() {
        return dateInsertion;
    }

    public void setDateInsertion(Date dateInsertion) {
        this.dateInsertion = dateInsertion;
    }

    public boolean getActif() {
        return actif;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public Categorie getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(Categorie idCategorie) {
        this.idCategorie = idCategorie;
    }

    @XmlTransient
    public Collection<Gestionstock> getGestionstockCollection() {
        return gestionstockCollection;
    }

    public void setGestionstockCollection(Collection<Gestionstock> gestionstockCollection) {
        this.gestionstockCollection = gestionstockCollection;
    }

    @XmlTransient
    public Collection<Lignefacture> getLignefactureCollection() {
        return lignefactureCollection;
    }

    public void setLignefactureCollection(Collection<Lignefacture> lignefactureCollection) {
        this.lignefactureCollection = lignefactureCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePro != null ? codePro.hashCode() : 0);
        return hash;
    }

    

    @Override
    public String toString() {
        return this.nomPro;
    }

    @Override
    public Integer getId() {
        return this.codePro;
    }

    public Produit(String nomPro, int prixVente, int prixAchat,  String description,Fournisseur codeFour,Categorie idCategorie) {
        this.codePro = null;
        this.nomPro = nomPro;
        this.prixVente = prixVente;
        this.prixAchat = prixAchat;
        this.qte = new BigDecimal(0);
        this.description = description;
        this.codeFour = codeFour;
        this.actif = true;
        this.dateInsertion = new Date();
        this.idCategorie = idCategorie;
        this.gestionstockCollection = null;
        this.lignefactureCollection = null;
    }

    public boolean isActif() {
        return actif;
    }
    
    public void activate(){
        EntityManager em = emf.createEntityManager();
        Produit prod = em.find(Produit.class,this.codePro);
        em.getTransaction().begin();
        prod.setActif(true);
        em.getTransaction().commit();
        em.close();
    }
    
    public void deactivate(){
        EntityManager em = emf.createEntityManager();
        Produit prod = em.find(Produit.class,this.codePro);
        em.getTransaction().begin();
        prod.setActif(false);
        em.getTransaction().commit();
        em.close();
    }
    
    public void toggleActivate(){
        EntityManager em = emf.createEntityManager();
        Produit prod = em.find(Produit.class,this.codePro);
        em.getTransaction().begin();
        prod.setActif(!prod.getActif());
        em.getTransaction().commit();
        em.close();
    }
    
    public static List<Produit> getProduits(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Produit> query = em.createQuery("select p from Produit p",Produit.class);
        return query.getResultList();
    }
    
    public static Produit getProduitByName(String name){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Produit> query = em.createQuery("Select c from Produit c where c.nomPro = :name",Produit.class).setParameter("name",name);
        return query.getSingleResult();
    }
    
    public static Produit getProduitByCode(Integer code){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Produit> query = em.createQuery("Select c from Produit c where c.codePro = :code",Produit.class).setParameter("code",code);
        return query.getSingleResult();
    }
    
    
    
    public void changeSellingPrice(Integer prix){
        EntityManager em = emf.createEntityManager();
        Produit prod = em.find(Produit.class,this.codePro);
        em.getTransaction().begin();
        prod.setPrixVente(prix);
        em.getTransaction().commit();
        em.close();
    }
    
    public void reduce(double qte){
        EntityManager em = emf.createEntityManager();
        Produit prod = em.find(Produit.class,this.codePro);
        em.getTransaction().begin();
        double newqty = prod.getQte().doubleValue() - qte;
        if(newqty < 0) newqty = 0;
        prod.setQte(new BigDecimal(newqty));
        em.getTransaction().commit();
        em.close();
    }
    
    public void increase(double qte){
        EntityManager em = emf.createEntityManager();
        Produit prod = em.find(Produit.class,this.codePro);
        em.getTransaction().begin();
        double newqty = prod.getQte().doubleValue() + qte;
        prod.setQte(new BigDecimal(newqty));
        em.getTransaction().commit();
        em.close();
    }

    public int numberOfSales(){
        int number = 0;
        for(Lignefacture ligne: this.lignefactureCollection){
            number = ligne.getQte().intValue() + number;
        }
        return number;
    }
    
    public double profitMade(){
        return this.numberOfSales() * (this.prixVente-this.prixAchat);
    }
    
    public static Comparator<Produit> sortByIdAsc = new Comparator<Produit>() {
        @Override
        public int compare(Produit o1, Produit o2) {
            return o1.codePro - o2.codePro;
        }
    };
    
    public static Comparator<Produit> sortByIdDesc = new Comparator<Produit>() {
        @Override
        public int compare(Produit o1, Produit o2) {
            return o2.codePro - o1.codePro;
        }
    };
    
    public static Comparator<Produit> sortBySales = new Comparator<Produit>() {
        @Override
        public int compare(Produit o1, Produit o2) {
            int ans = o2.numberOfSales() - o1.numberOfSales();
            return ans;
        }
    };
    
    public static Comparator<Produit> sortByProfitMade = new Comparator<Produit>() {
        @Override
        public int compare(Produit o1, Produit o2) {
            return Double.valueOf(o2.profitMade() - o1.profitMade()).intValue();
        }
    };

    public Boolean getWholeOnly() {
        return wholeOnly;
    }

    public void setWholeOnly(Boolean wholeOnly) {
        this.wholeOnly = wholeOnly;
    }

    public Produit(Boolean wholeOnly,String nomPro, Integer prixVente, int prixAchat, String description, Fournisseur codeFour, Categorie idCategorie) {
        this.wholeOnly = wholeOnly;
        this.codePro = null;
        this.nomPro = nomPro;
        this.prixVente = prixVente;
        this.prixAchat = prixAchat;
        this.qte = new BigDecimal(0);
        this.description = description;
        this.codeFour = codeFour;
        this.dateInsertion = new Date();
        this.actif = true;
        this.idCategorie = idCategorie;
    }

    public BigDecimal getQte() {
        return qte;
    }

    public void setQte(BigDecimal qte) {
        this.qte = qte;
    }
    
    public String getCode(){
        String code = "";
        String pro = this.codePro.toString();
        for(int i=0;i<pro.length();i++){
            code += pro.charAt(i);
        }
        for(int i=pro.length();i<6;i++){
            code = "0" + code;
        }
        String result = "";
        for(int i=0;i<code.length();i++){
            if(i == 3){
                result += '-';
            }
            result+= code.charAt(i);
        }
        return result;
    }
    
    public static Integer codeToInt(String code){
        try{
            if(code.charAt(0) != '0'){
                return Integer.valueOf(code.replaceAll("-",""));
            }
            String res = "";
            for(int i= code.indexOf('-') +1 ; i<code.length();i++){
                res += code.charAt(i);
            }
            return Integer.valueOf(res);
        }catch(NumberFormatException e){
            return 0;
        }
    }
    
}
