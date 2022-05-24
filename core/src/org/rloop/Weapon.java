package org.rloop;

public abstract class Weapon {
    float weaponDamage;
    float weaponAttackSpeed;

    public Weapon(float dam, float speed){
        weaponDamage = dam;
        weaponAttackSpeed = speed;
    }

    public void attack(Player player, float cursorX, float cursorY){}

    public void specialAttack(Player player, float cursorX, float cursorY){}

    public float getWeaponDamage(){
        return this.weaponDamage;
    }

    public float getWeaponAttackSpeed(){
        return this.weaponAttackSpeed;
    }

}
