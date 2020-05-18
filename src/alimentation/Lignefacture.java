/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alimentation;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jhy
 */
@Entity
@Table(name = "lignefacture")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lignefacture.findAll", query = "SELECT l FROM Lignefacture l")
    , @NamedQuery(name = "Lignefacture.findByIdLFac", query = "SELECT l FROM Lignefacture l WHERE l.idLFac = :idLFac")
    , @NamedQuery(name = "Lignefacture.findByPrix", query = "SELECT l FROM Lignefacture l WHERE l.prix = :prix")
    , @NamedQuery(name = "Lignefacture.findByQte", query = "SELECT l FROM Lignefacture l WHERE l.qte = :qte")})
public class Lignefacture implements Serializable ,EntityClasses{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idLFac")
    private Integer idLFac;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @Column(name = "prix")
    private BigDecimal prix;
    @Basic(optional = false)
    @Column(name = "qte")
    private short qte;
    @JoinColumn(name = "codePro", referencedColumnName = "codePro")
    @ManyToOne(optional = false)
    private Produit codePro;
    @JoinColumn(name = "idFac", referencedColumnName = "idFac")
    @ManyToOne(optional = false)
    private Facture idFac;

    public Lignefacture() {
    }

    public Lignefacture(Integer idLFac) {
        this.idLFac = idLFac;
    }

    public Lignefacture(Integer idLFac, BigDecimal prix, short qte) {
        this.idLFac = idLFac;
        this.prix = prix;
        this.qte = qte;
    }

    public Integer getIdLFac() {
        return idLFac;
    }

    public void setIdLFac(Integer idLFac) {
        this.idLFac = idLFac;
    }

    public BigDecimal getPrix() {
        return prix;
    }

    public void setPrix(BigDecimal prix) {
        this.prix = prix;
    }

    public short getQte() {
        return qte;
    }

    public void setQte(short qte) {
        this.qte = qte;
    }

    public Produit getCodePro() {
        return codePro;
    }

    public void setCodePro(Produit codePro) {
        this.codePro = codePro;
    }

    public Facture getIdFac() {
        return idFac;
    }

    public void setIdFac(Facture idFac) {
        this.idFac = idFac;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idLFac != null ? idLFac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignefacture)) {
            return false;
        }
        Lignefacture other = (Lignefacture) object;
        if ((this.idLFac == null && other.idLFac != null) || (this.idLFac != null && !this.idLFac.equals(other.idLFac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "alimentation.Lignefacture[ idLFac=" + idLFac + " ]";
    }

    @Override
    public Integer getId() {
        return this.idLFac;
    }
    
}
