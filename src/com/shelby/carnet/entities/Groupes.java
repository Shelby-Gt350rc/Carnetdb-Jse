/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "groupes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Groupes.findAll", query = "SELECT g FROM Groupes g"),
    @NamedQuery(name = "Groupes.findByIdGrps", query = "SELECT g FROM Groupes g WHERE g.idGrps = :idGrps"),
    @NamedQuery(name = "Groupes.findByCodeGrps", query = "SELECT g FROM Groupes g WHERE g.codeGrps = :codeGrps"),
    @NamedQuery(name = "Groupes.findByIntituleGrps", query = "SELECT g FROM Groupes g WHERE g.intituleGrps = :intituleGrps"),
    @NamedQuery(name = "Groupes.findByActiveGrps", query = "SELECT g FROM Groupes g WHERE g.activeGrps = :activeGrps")})
public class Groupes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idGrps")
    private Integer idGrps;
    @Column(name = "codeGrps")
    private String codeGrps;
    @Column(name = "intituleGrps")
    private String intituleGrps;
    @Basic(optional = false)
    @Lob
    @Column(name = "urlPhotosGrps")
    private String urlPhotosGrps;
    @Column(name = "activeGrps")
    private String activeGrps;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrps")
    private List<Comptes> comptesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrps")
    private List<Configurations> configurationsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idGrps")
    private List<Contacts> contactsList;

    public Groupes() {
    }

    public Groupes(Integer idGrps) {
        this.idGrps = idGrps;
    }

    public Groupes(Integer idGrps, String urlPhotosGrps) {
        this.idGrps = idGrps;
        this.urlPhotosGrps = urlPhotosGrps;
    }

    public Integer getIdGrps() {
        return idGrps;
    }

    public void setIdGrps(Integer idGrps) {
        this.idGrps = idGrps;
    }

    public String getCodeGrps() {
        return codeGrps;
    }

    public void setCodeGrps(String codeGrps) {
        this.codeGrps = codeGrps;
    }

    public String getIntituleGrps() {
        return intituleGrps;
    }

    public void setIntituleGrps(String intituleGrps) {
        this.intituleGrps = intituleGrps;
    }

    public String getUrlPhotosGrps() {
        return urlPhotosGrps;
    }

    public void setUrlPhotosGrps(String urlPhotosGrps) {
        this.urlPhotosGrps = urlPhotosGrps;
    }

    public String getActiveGrps() {
        return activeGrps;
    }

    public void setActiveGrps(String activeGrps) {
        this.activeGrps = activeGrps;
    }

    @XmlTransient
    public List<Comptes> getComptesList() {
        return comptesList;
    }

    public void setComptesList(List<Comptes> comptesList) {
        this.comptesList = comptesList;
    }

    @XmlTransient
    public List<Configurations> getConfigurationsList() {
        return configurationsList;
    }

    public void setConfigurationsList(List<Configurations> configurationsList) {
        this.configurationsList = configurationsList;
    }

    @XmlTransient
    public List<Contacts> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGrps != null ? idGrps.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Groupes)) {
            return false;
        }
        Groupes other = (Groupes) object;
        if ((this.idGrps == null && other.idGrps != null) || (this.idGrps != null && !this.idGrps.equals(other.idGrps))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shelby.carnet.entities.Groupes[ idGrps=" + idGrps + " ]";
    }
    
}
