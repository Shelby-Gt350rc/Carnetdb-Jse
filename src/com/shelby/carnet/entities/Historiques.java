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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shelby
 */
@Entity
@Table(name = "historiques")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Historiques.findAll", query = "SELECT h FROM Historiques h"),
    @NamedQuery(name = "Historiques.findByIdHisto", query = "SELECT h FROM Historiques h WHERE h.idHisto = :idHisto"),
    @NamedQuery(name = "Historiques.findByJoursHisto", query = "SELECT h FROM Historiques h WHERE h.joursHisto = :joursHisto"),
    @NamedQuery(name = "Historiques.findByLegendeHisto", query = "SELECT h FROM Historiques h WHERE h.legendeHisto = :legendeHisto")})
public class Historiques implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idHisto")
    private Integer idHisto;
    @Basic(optional = false)
    @Column(name = "joursHisto")
    private String joursHisto;
    @Basic(optional = false)
    @Column(name = "legendeHisto")
    private String legendeHisto;
    @Basic(optional = false)
    @Lob
    @Column(name = "ancienneInfos")
    private String ancienneInfos;
    @Basic(optional = false)
    @Lob
    @Column(name = "nouvelleInfos")
    private String nouvelleInfos;
    @JoinColumn(name = "idCpts", referencedColumnName = "idCpts")
    @ManyToOne
    private Comptes idCpts;
    @JoinColumn(name = "idconfig", referencedColumnName = "idconfig")
    @ManyToOne
    private Configurations idconfig;
    @JoinColumn(name = "idCts", referencedColumnName = "idCts")
    @ManyToOne
    private Contacts idCts;

    public Historiques() {
    }

    public Historiques(Integer idHisto) {
        this.idHisto = idHisto;
    }

    public Historiques(Integer idHisto, String joursHisto, String legendeHisto, String ancienneInfos, String nouvelleInfos) {
        this.idHisto = idHisto;
        this.joursHisto = joursHisto;
        this.legendeHisto = legendeHisto;
        this.ancienneInfos = ancienneInfos;
        this.nouvelleInfos = nouvelleInfos;
    }

    public Integer getIdHisto() {
        return idHisto;
    }

    public void setIdHisto(Integer idHisto) {
        this.idHisto = idHisto;
    }

    public String getJoursHisto() {
        return joursHisto;
    }

    public void setJoursHisto(String joursHisto) {
        this.joursHisto = joursHisto;
    }

    public String getLegendeHisto() {
        return legendeHisto;
    }

    public void setLegendeHisto(String legendeHisto) {
        this.legendeHisto = legendeHisto;
    }

    public String getAncienneInfos() {
        return ancienneInfos;
    }

    public void setAncienneInfos(String ancienneInfos) {
        this.ancienneInfos = ancienneInfos;
    }

    public String getNouvelleInfos() {
        return nouvelleInfos;
    }

    public void setNouvelleInfos(String nouvelleInfos) {
        this.nouvelleInfos = nouvelleInfos;
    }

    public Comptes getIdCpts() {
        return idCpts;
    }

    public void setIdCpts(Comptes idCpts) {
        this.idCpts = idCpts;
    }

    public Configurations getIdconfig() {
        return idconfig;
    }

    public void setIdconfig(Configurations idconfig) {
        this.idconfig = idconfig;
    }

    public Contacts getIdCts() {
        return idCts;
    }

    public void setIdCts(Contacts idCts) {
        this.idCts = idCts;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idHisto != null ? idHisto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Historiques)) {
            return false;
        }
        Historiques other = (Historiques) object;
        if ((this.idHisto == null && other.idHisto != null) || (this.idHisto != null && !this.idHisto.equals(other.idHisto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shelby.carnet.entities.Historiques[ idHisto=" + idHisto + " ]";
    }
    
}
