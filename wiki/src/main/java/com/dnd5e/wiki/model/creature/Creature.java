package com.dnd5e.wiki.model.creature;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="monstres")
@Data
public class Creature
{
  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;
  private String name;
  
  @Enumerated(EnumType.ORDINAL)
  private CreatureSize size;
  
  @Enumerated(EnumType.ORDINAL)
  private Alignment alignment;
  
  @Enumerated(EnumType.ORDINAL)
  private CreatureType type;
  
  private byte AC;
  
  @Enumerated(EnumType.ORDINAL)
  private ArmorType armorType;
  
  private short countHpBone;
  
  @Enumerated(EnumType.ORDINAL)
  private Bone hpBone;
  
  private short bonusHP;

  private short speed;
  private short sppedFly;
  private short strength;
  private short dexterity;
  private short constitution;
  private short intellect;
  private short wizdom;
  private short charisma;
  private String senses;
  private short passivePerception;
  private int exp;
  private String languages;
  @OneToMany
  private List<Feat> feats;
  @OneToMany
  private List<Action> actions;
   
  public String getCR()
  {
    String expStr = "0";
    if ((this.exp > 10) && (this.exp <= 25)) {
      expStr = "1/8";
    } else if ((this.exp > 25) && (this.exp <= 50)) {
      expStr = "1/8";
    } else if ((this.exp > 50) && (this.exp <= 100)) {
      expStr = "1/4";
    } else if ((this.exp > 100) && (this.exp <= 200)) {
      expStr = "1/2";
    } else if ((this.exp > 200) && (this.exp <= 450)) {
      expStr = "1";
    } else if ((this.exp > 450) && (this.exp <= 700)) {
      expStr = "2";
    } else if ((this.exp > 700) && (this.exp <= 1100)) {
      expStr = "3";
    } else if ((this.exp > 1100) && (this.exp <= 1800)) {
      expStr = "4";
    } else if ((this.exp > 1800) && (this.exp <= 2300)) {
      expStr = "5";
    } else if ((this.exp > 2300) && (this.exp <= 2900)) {
      expStr = "6";
    } else if ((this.exp > 2900) && (this.exp <= 3900)) {
      expStr = "7";
    } else if ((this.exp > 3900) && (this.exp <= 5000)) {
      expStr = "8";
    } else if ((this.exp > 5000) && (this.exp <= 5900)) {
      expStr = "9";
    } else if ((this.exp > 5900) && (this.exp <= 7200)) {
      expStr = "10";
    } else if ((this.exp > 7200) && (this.exp <= 8400)) {
      expStr = "11";
    } else if ((this.exp > 8400) && (this.exp <= 10000)) {
      expStr = "12";
    } else if ((this.exp > 10000) && (this.exp <= 11500)) {
      expStr = "13";
    } else if ((this.exp > 11500) && (this.exp <= 13000)) {
      expStr = "14";
    } else if ((this.exp > 13000) && (this.exp <= 15000)) {
      expStr = "15";
    } else if ((this.exp > 15000) && (this.exp <= 18000)) {
      expStr = "16";
    } else if ((this.exp > 18000) && (this.exp <= 20000)) {
      expStr = "17";
    } else if ((this.exp > 20000) && (this.exp <= 22000)) {
      expStr = "18";
    } else if ((this.exp > 22000) && (this.exp <= 25000)) {
      expStr = "19";
    } else if ((this.exp > 25000) && (this.exp <= 33000)) {
      expStr = "20";
    } else if ((this.exp > 33000) && (this.exp <= 41000)) {
      expStr = "21";
    } else if ((this.exp > 41000) && (this.exp <= 50000)) {
      expStr = "22";
    } else if ((this.exp > 50000) && (this.exp <= 62000)) {
      expStr = "23";
    } else if ((this.exp > 62000) && (this.exp <= 75000)) {
      expStr = "24";
    } else if ((this.exp > 75000) && (this.exp <= 90000)) {
      expStr = "25";
    } else if ((this.exp > 90000) && (this.exp <= 105000)) {
      expStr = "26";
    } else if ((this.exp > 105000) && (this.exp <= 120000)) {
      expStr = "27";
    } else if ((this.exp > 120000) && (this.exp <= 135000)) {
      expStr = "28";
    } else if ((this.exp > 135000) && (this.exp <= 155000)) {
      expStr = "29";
    } else if (this.exp > 155000) {
      expStr = "30";
    }
    return expStr;
  }
}
