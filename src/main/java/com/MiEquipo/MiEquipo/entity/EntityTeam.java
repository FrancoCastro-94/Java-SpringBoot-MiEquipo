
package com.MiEquipo.MiEquipo.entity;

import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class EntityTeam {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid" , strategy = "uuid")
    private String id;
    @Column
    private String name;
    @Column
    private String gender;
    @Column
    private String creator;
    @Column
    private String sizeTeam;
    @Column
    private String city;
    @Column
    private String provincia;
    
    @ManyToMany
    private Set<EntityPlayer> players;
    
    @OneToOne
    private EntityPhoto photo;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;
    
    
    
    //Getters and Setters

    /**
     * @return the ddddu
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the ddddu to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender the gender to set
     */
    public void setGender(String gender) {
        this.gender = gender;
    }


    /**
     * @return the teamSize
     */
    public String getSizeTeam() {
        return sizeTeam;
    }

    /**
     * @param sizeTeam the teamSize to set
     */
    public void setSizeTeam(String sizeTeam) {
        this.sizeTeam = sizeTeam;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * @param povincia the provincia to set
     */
    public void setProvincia(String povincia) {
        this.provincia = povincia;
    }

    /**
     * @return the players
     */
    public Set<EntityPlayer> getPlayers() {
        return players;
    }

    /**
     * @param players the players to set
     */
    public void setPlayers(Set<EntityPlayer> players) {
        this.players = players;
    }

    /**
     * @return the photo
     */
    public EntityPhoto getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(EntityPhoto photo) {
        this.photo = photo;
    }

    /**
     * @return the alta
     */
    public Date getAlta() {
        return alta;
    }

    /**
     * @param alta the alta to set
     */
    public void setAlta(Date alta) {
        this.alta = alta;
    }

    /**
     * @return the creator
     */
    public String getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * @return the baja
     */
    public Date getBaja() {
        return baja;
    }

    /**
     * @param baja the baja to set
     */
    public void setBaja(Date baja) {
        this.baja = baja;
    }

    
    
    
    
}
