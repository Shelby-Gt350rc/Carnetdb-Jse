/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shelby
 */
@Entity
@Table(name = "utilisateur")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
    @NamedQuery(name = "Utilisateur.findByIdUsr", query = "SELECT u FROM Utilisateur u WHERE u.idUsr = :idUsr"),
    @NamedQuery(name = "Utilisateur.findByLoginUsr", query = "SELECT u FROM Utilisateur u WHERE u.loginUsr = :loginUsr"),
    @NamedQuery(name = "Utilisateur.findByPasswordUsr", query = "SELECT u FROM Utilisateur u WHERE u.passwordUsr = :passwordUsr"),
    @NamedQuery(name = "Utilisateur.findByDescriptionUsr", query = "SELECT u FROM Utilisateur u WHERE u.descriptionUsr = :descriptionUsr"),
    @NamedQuery(name = "Utilisateur.findByActiveUsr", query = "SELECT u FROM Utilisateur u WHERE u.activeUsr = :activeUsr")})
public class Utilisateur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idUsr")
    private Integer idUsr;
    @Basic(optional = false)
    @Column(name = "loginUsr")
    private String loginUsr;
    @Basic(optional = false)
    @Column(name = "passwordUsr")
    private String passwordUsr;
    @Lob
    @Column(name = "urlPhotosUsr")
    private String urlPhotosUsr;
    @Basic(optional = false)
    @Column(name = "descriptionUsr")
    private String descriptionUsr;
    @Column(name = "activeUsr")
    private String activeUsr;

    public Utilisateur() {
    }

    public Utilisateur(Integer idUsr) {
        this.idUsr = idUsr;
    }

    public Utilisateur(Integer idUsr, String loginUsr, String passwordUsr, String descriptionUsr) {
        this.idUsr = idUsr;
        this.loginUsr = loginUsr;
        this.passwordUsr = passwordUsr;
        this.descriptionUsr = descriptionUsr;
    }

    public Integer getIdUsr() {
        return idUsr;
    }

    public void setIdUsr(Integer idUsr) {
        this.idUsr = idUsr;
    }

    public String getLoginUsr() {
        return loginUsr;
    }

    public void setLoginUsr(String loginUsr) {
        this.loginUsr = loginUsr;
    }

    public String getPasswordUsr() {
        return passwordUsr;
    }

    public void setPasswordUsr(String passwordUsr) {
        this.passwordUsr = passwordUsr;
    }

    public String getUrlPhotosUsr() {
        return urlPhotosUsr;
    }

    public void setUrlPhotosUsr(String urlPhotosUsr) {
        this.urlPhotosUsr = urlPhotosUsr;
    }

    public String getDescriptionUsr() {
        return descriptionUsr;
    }

    public void setDescriptionUsr(String descriptionUsr) {
        this.descriptionUsr = descriptionUsr;
    }

    public String getActiveUsr() {
        return activeUsr;
    }

    public void setActiveUsr(String activeUsr) {
        this.activeUsr = activeUsr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsr != null ? idUsr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.idUsr == null && other.idUsr != null) || (this.idUsr != null && !this.idUsr.equals(other.idUsr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shelby.carnet.entities.Utilisateur[ idUsr=" + idUsr + " ]";
    }
    
}
