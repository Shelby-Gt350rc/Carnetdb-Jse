/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shelby
 */
@Entity
@Table(name = "comptes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comptes.findAll", query = "SELECT c FROM Comptes c"),
    @NamedQuery(name = "Comptes.findByIdCpts", query = "SELECT c FROM Comptes c WHERE c.idCpts = :idCpts"),
    @NamedQuery(name = "Comptes.findByCodeCpts", query = "SELECT c FROM Comptes c WHERE c.codeCpts = :codeCpts"),
    @NamedQuery(name = "Comptes.findByIntituleCpts", query = "SELECT c FROM Comptes c WHERE c.intituleCpts = :intituleCpts"),
    @NamedQuery(name = "Comptes.findByLoginCpts", query = "SELECT c FROM Comptes c WHERE c.loginCpts = :loginCpts"),
    @NamedQuery(name = "Comptes.findByPasswordCpts", query = "SELECT c FROM Comptes c WHERE c.passwordCpts = :passwordCpts"),
    @NamedQuery(name = "Comptes.findByTypeSecuriteCpts", query = "SELECT c FROM Comptes c WHERE c.typeSecuriteCpts = :typeSecuriteCpts"),
    @NamedQuery(name = "Comptes.findByActiveCpts", query = "SELECT c FROM Comptes c WHERE c.activeCpts = :activeCpts")})
public class Comptes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCpts")
    private Integer idCpts;
    @Column(name = "codeCpts")
    private String codeCpts;
    @Column(name = "intituleCpts")
    private String intituleCpts;
    @Column(name = "loginCpts")
    private String loginCpts;
    @Column(name = "passwordCpts")
    private String passwordCpts;
    @Column(name = "typeSecuriteCpts")
    private String typeSecuriteCpts;
    @Column(name = "activeCpts")
    private Boolean activeCpts;
    @Lob
    @Column(name = "urlPhotosCpts")
    private String urlPhotosCpts;
    @OneToMany(mappedBy = "idCpts")
    private List<Historiques> historiquesList;
    @JoinColumn(name = "idGrps", referencedColumnName = "idGrps")
    @ManyToOne(optional = false)
    private Groupes idGrps;

    public Comptes() {
    }

    public Comptes(Integer idCpts) {
        this.idCpts = idCpts;
    }

    public Integer getIdCpts() {
        return idCpts;
    }

    public void setIdCpts(Integer idCpts) {
        this.idCpts = idCpts;
    }

    public String getCodeCpts() {
        return codeCpts;
    }

    public void setCodeCpts(String codeCpts) {
        this.codeCpts = codeCpts;
    }

    public String getIntituleCpts() {
        return intituleCpts;
    }

    public void setIntituleCpts(String intituleCpts) {
        this.intituleCpts = intituleCpts;
    }

    public String getLoginCpts() {
        return loginCpts;
    }

    public void setLoginCpts(String loginCpts) {
        this.loginCpts = loginCpts;
    }

    public String getPasswordCpts() {
        return passwordCpts;
    }

    public void setPasswordCpts(String passwordCpts) {
        this.passwordCpts = passwordCpts;
    }

    public String getTypeSecuriteCpts() {
        return typeSecuriteCpts;
    }

    public void setTypeSecuriteCpts(String typeSecuriteCpts) {
        this.typeSecuriteCpts = typeSecuriteCpts;
    }

    public Boolean getActiveCpts() {
        return activeCpts;
    }

    public void setActiveCpts(Boolean activeCpts) {
        this.activeCpts = activeCpts;
    }

    public String getUrlPhotosCpts() {
        return urlPhotosCpts;
    }

    public void setUrlPhotosCpts(String urlPhotosCpts) {
        this.urlPhotosCpts = urlPhotosCpts;
    }

    @XmlTransient
    public List<Historiques> getHistoriquesList() {
        return historiquesList;
    }

    public void setHistoriquesList(List<Historiques> historiquesList) {
        this.historiquesList = historiquesList;
    }

    public Groupes getIdGrps() {
        return idGrps;
    }

    public void setIdGrps(Groupes idGrps) {
        this.idGrps = idGrps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCpts != null ? idCpts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comptes)) {
            return false;
        }
        Comptes other = (Comptes) object;
        if ((this.idCpts == null && other.idCpts != null) || (this.idCpts != null && !this.idCpts.equals(other.idCpts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shelby.carnet.entities.Comptes[ idCpts=" + idCpts + " ]";
    }
    
}
