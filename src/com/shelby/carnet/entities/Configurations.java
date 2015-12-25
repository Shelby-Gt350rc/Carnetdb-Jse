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
@Table(name = "configurations")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Configurations.findAll", query = "SELECT c FROM Configurations c"),
    @NamedQuery(name = "Configurations.findByIdconfig", query = "SELECT c FROM Configurations c WHERE c.idconfig = :idconfig"),
    @NamedQuery(name = "Configurations.findByCodeConfig", query = "SELECT c FROM Configurations c WHERE c.codeConfig = :codeConfig"),
    @NamedQuery(name = "Configurations.findByIntituleConfig", query = "SELECT c FROM Configurations c WHERE c.intituleConfig = :intituleConfig"),
    @NamedQuery(name = "Configurations.findByActiveConfig", query = "SELECT c FROM Configurations c WHERE c.activeConfig = :activeConfig")})
public class Configurations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idconfig")
    private Integer idconfig;
    @Basic(optional = false)
    @Column(name = "codeConfig")
    private String codeConfig;
    @Basic(optional = false)
    @Column(name = "intituleConfig")
    private String intituleConfig;
    @Basic(optional = false)
    @Lob
    @Column(name = "infosConfig")
    private String infosConfig;
    @Basic(optional = false)
    @Column(name = "activeConfig")
    private boolean activeConfig;
    @OneToMany(mappedBy = "idconfig")
    private List<Historiques> historiquesList;
    @JoinColumn(name = "idGrps", referencedColumnName = "idGrps")
    @ManyToOne(optional = false)
    private Groupes idGrps;

    public Configurations() {
    }

    public Configurations(Integer idconfig) {
        this.idconfig = idconfig;
    }

    public Configurations(Integer idconfig, String codeConfig, String intituleConfig, String infosConfig, boolean activeConfig) {
        this.idconfig = idconfig;
        this.codeConfig = codeConfig;
        this.intituleConfig = intituleConfig;
        this.infosConfig = infosConfig;
        this.activeConfig = activeConfig;
    }

    public Integer getIdconfig() {
        return idconfig;
    }

    public void setIdconfig(Integer idconfig) {
        this.idconfig = idconfig;
    }

    public String getCodeConfig() {
        return codeConfig;
    }

    public void setCodeConfig(String codeConfig) {
        this.codeConfig = codeConfig;
    }

    public String getIntituleConfig() {
        return intituleConfig;
    }

    public void setIntituleConfig(String intituleConfig) {
        this.intituleConfig = intituleConfig;
    }

    public String getInfosConfig() {
        return infosConfig;
    }

    public void setInfosConfig(String infosConfig) {
        this.infosConfig = infosConfig;
    }

    public boolean getActiveConfig() {
        return activeConfig;
    }

    public void setActiveConfig(boolean activeConfig) {
        this.activeConfig = activeConfig;
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
        hash += (idconfig != null ? idconfig.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Configurations)) {
            return false;
        }
        Configurations other = (Configurations) object;
        if ((this.idconfig == null && other.idconfig != null) || (this.idconfig != null && !this.idconfig.equals(other.idconfig))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shelby.carnet.entities.Configurations[ idconfig=" + idconfig + " ]";
    }
    
}
