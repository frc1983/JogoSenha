
package Enumerators;

public enum GameColors {
    AZUL, VERMELHO, AMARELO, VERDE, ROXO, ROSA;
    
    public static GameColors getRandom() {
        return values()[(int) (Math.random() * values().length)];
    }
    
    public static GameColors getColor(int index) {
        return values()[index];
    }
}
