/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alimentation;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jhy
 */
@Entity
@Table(name = "gestionstock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gestionstock.findAll", query = "SELECT g FROM Gestionstock g")
    , @NamedQuery(name = "Gestionstock.findByIdStock", query = "SELECT g FROM Gestionstock g WHERE g.idStock = :idStock")
    , @NamedQuery(name = "Gestionstock.findByQte", query = "SELECT g FROM Gestionstock g WHERE g.qte = :qte")
    , @NamedQuery(name = "Gestionstock.findByDateStock", query = "SELECT g FROM Gestionstock g WHERE g.dateStock = :dateStock")
    , @NamedQuery(name = "Gestionstock.findByOperation", query = "SELECT g FROM Gestionstock g WHERE g.operation = :operation")})
public class Gestionstock implements Serializable ,EntityClasses,Transaction{

    @Column(name = "description")
    private String description;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idStock")
    private Integer idStock;
    @Basic(optional = false)
    @Column(name = "qte")
    private int qte;
    @Basic(optional = false)
    @Column(name = "dateStock")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStock;
    @Basic(optional = false)
    @Column(name = "operation")
    private boolean operation;
    @JoinColumn(name = "codePro", referencedColumnName = "codePro")
    @ManyToOne(optional = false)
    private Produit codePro;
    @JoinColumn(name = "idGest", referencedColumnName = "idGest")
    @ManyToOne(optional = false)
    private Gestionnaire idGest;

    public Gestionstock() {
    }

    public Gestionstock(Integer idStock) {
        this.idStock = idStock;
    }

    public Gestionstock(Integer idStock, int qte, Date dateStock, boolean operation) {
        this.idStock = idStock;
        this.qte = qte;
        this.dateStock = dateStock;
        this.operation = operation;
        this.description = "";
    }
    
    public Gestionstock(Integer idStock, int qte, Date dateStock, boolean operation,String description) {
        this.idStock = idStock;
        this.qte = qte;
        this.dateStock = dateStock;
        this.operation = operation;
        this.description = description;
    }
    public Integer getIdStock() {
        return idStock;
    }

    public void setIdStock(Integer idStock) {
        this.idStock = idStock;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public Date getDateStock() {
        return dateStock;
    }

    public void setDateStock(Date dateStock) {
        this.dateStock = dateStock;
    }

    public boolean getOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

    public Produit getCodePro() {
        return codePro;
    }

    public void setCodePro(Produit codePro) {
        this.codePro = codePro;
    }

    public Gestionnaire getIdGest() {
        return idGest;
    }

    public void setIdGest(Gestionnaire idGest) {
        this.idGest = idGest;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idStock != null ? idStock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        Gestionstock other = (Gestionstock) object;
        return this.idStock.intValue() == other.idStock.intValue();
    }

    @Override
    public String toString() {
        return "alimentation.Gestionstock[ idStock=" + idStock + " ]";
    }

    @Override
    public Integer getId() {
        return this.idStock;
    }

    public Gestionstock(int qte, boolean operation, Produit codePro, Gestionnaire idGest) {
        this.idStock = null;
        this.qte = qte;
        this.dateStock = new Date();
        this.operation = operation;
        this.codePro = codePro;
        this.idGest = idGest;
    }
    
    public static List<Gestionstock> getTransactions(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<Gestionstock> query;
        query = em.createQuery("select g from Gestionstock g",Gestionstock.class);
        return query.getResultList();
    }

    public boolean isIncrease() {
        return operation;
    }
    
    
    public void transaction(){
        if(this.isIncrease()){
            this.codePro.increase(this.qte);
        }else{
            this.codePro.reduce(this.qte);
        }
    }
    
    public boolean canReduce(){
        return this.codePro.getQte() >= this.qte;
    }
    
    public static Comparator<Gestionstock> sortByIddesc = new Comparator<Gestionstock>() {
        @Override
        public int compare(Gestionstock o1, Gestionstock o2) {
            return o2.idStock - o1.idStock;
        }
    };
    
    @Override
    public double trans() {
        return -1*this.getQte() * this.codePro.getPrixAchat();
    }
    
    @Override
    public Date getDate(){
        return this.dateStock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    
}
