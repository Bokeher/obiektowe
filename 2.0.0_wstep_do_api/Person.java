public class Person {
    private String imie;
    private int wiek;
    private int numer;

    public Person(String imie, int wiek, int numer) {
        this.imie = imie;
        this.wiek = wiek;
        this.numer = numer;
    }   

    public String getImie() {
        return imie;
    }
    public int getWiek() {
        return wiek;
    }
    public int getNumer() {
        return numer;
    }

    @Override
    public String toString() {
        return "\nImię: "+imie+"\n"+"Wiek: "+wiek+"\n"+"Numer: "+numer;
    }

}
