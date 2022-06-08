package org.rloop;

public abstract class Weapon implements Items {
    float weaponDamage;
    float weaponAttackSpeed;
    Player player;

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

    public void pickUp(Player player) {
        player.playerWeapon = this;
        player.mainScreen.itemList.add(this);
    }

}
