import java.io.*;
import java.util.*;

class _20010310016_Kapi {
    String tur;
    int girisSayisi;
    List<String> girisler;

    public _20010310016_Kapi(String tur) {
        this.tur = tur;
        this.girisler = new ArrayList<>();
    }
}

class _20010310016_Devre {
    List<List<_20010310016_Kapi>> seviyeler;
    Map<String, Boolean> degiskenler;
    Map<String, Boolean> araDegerler;

    public _20010310016_Devre() {
        this.seviyeler = new ArrayList<>();
        this.degiskenler = new HashMap<>();
        this.araDegerler = new HashMap<>();
    }

    public void calistir() {
        for (List<_20010310016_Kapi> seviye : seviyeler) {
            for (_20010310016_Kapi kapi : seviye) {
                boolean sonuc;
                if (kapi.tur.equals("DEÐÝL")) {
                    boolean giris = getDeger(kapi.girisler.get(0));
                    sonuc = !giris;
                } else if (kapi.tur.equals("VE")) {
                    sonuc = true;
                    for (String giris : kapi.girisler) {
                        sonuc &= getDeger(giris);
                    }
                } else {
                    sonuc = false;
                    for (String giris : kapi.girisler) {
                        sonuc |= getDeger(giris);
                    }
                }
                araDegerler.put(kapi.tur + seviyeler.indexOf(seviye) + seviye.indexOf(kapi), sonuc);
            }
        }
    }

    private boolean getDeger(String giris) {
        if (degiskenler.containsKey(giris)) return degiskenler.get(giris);
        if (araDegerler.containsKey(giris)) return araDegerler.get(giris);
        return false;
    }
}

public class _20010310016_Boole {
    public static void main(String[] args) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("boole.txt"));
            String line = reader.readLine();
            reader.close();

            String fonksiyon = line.split("=")[1].trim();
            String[] terimler = fonksiyon.split("\\+");

            _20010310016_Devre devre = new _20010310016_Devre();
            List<_20010310016_Kapi> seviye1 = new ArrayList<>();
            Set<String> degiskenler = new HashSet<>();

            for (String terim : terimler) {
                terim = terim.trim();
                for (int i = 0; i < terim.length(); i++) {
                    char c = terim.charAt(i);
                    if (Character.isLetter(c)) {
                        degiskenler.add(String.valueOf(c));
                        if (i + 1 < terim.length() && terim.charAt(i + 1) == '\'') {
                            _20010310016_Kapi degil = new _20010310016_Kapi("DEÐÝL");
                            degil.girisler.add(String.valueOf(c));
                            seviye1.add(degil);
                        }
                    }
                }
            }

            if (!seviye1.isEmpty()) {
                devre.seviyeler.add(seviye1);
            }

            List<_20010310016_Kapi> seviye2 = new ArrayList<>();
            for (String terim : terimler) {
                terim = terim.trim();
                if (terim.length() > 1) {
                    _20010310016_Kapi ve = new _20010310016_Kapi("VE");
                    for (int i = 0; i < terim.length(); i++) {
                        char c = terim.charAt(i);
                        if (Character.isLetter(c)) {
                            if (i + 1 < terim.length() && terim.charAt(i + 1) == '\'') {
                                ve.girisler.add("DEÐÝL" + ((devre.seviyeler.size() - 1) + seviye1.size() - 1));
                                i++;
                            } else {
                                ve.girisler.add(String.valueOf(c));
                            }
                        }
                    }
                    seviye2.add(ve);
                }
            }

            if (!seviye2.isEmpty()) {
                devre.seviyeler.add(seviye2);
            }

            if (terimler.length > 1) {
                List<_20010310016_Kapi> seviye3 = new ArrayList<>();
                _20010310016_Kapi veya = new _20010310016_Kapi("VEYA");
                for (String terim : terimler) {
                    terim = terim.trim();
                    if (terim.length() == 1) {
                        if (terim.charAt(0) == '\'') continue;
                        veya.girisler.add(terim);
                    } else {
                        int index = Arrays.asList(terimler).indexOf(terim);
                        veya.girisler.add("VE" + (devre.seviyeler.size() - 1) + index);
                    }
                }
                seviye3.add(veya);
                devre.seviyeler.add(seviye3);
            }

            System.out.println("boole.txt dosyasý okundu.");
            System.out.println("Devre " + devre.seviyeler.size() + " seviyelidir.");

            for (int i = 0; i < devre.seviyeler.size(); i++) {
                List<_20010310016_Kapi> seviye = devre.seviyeler.get(i);
                System.out.println((i + 1) + ". Seviye için:");
                System.out.println("Kapý türü: " + seviye.get(0).tur + ", " + seviye.size() + " tane kapý var");

                for (int j = 0; j < seviye.size(); j++) {
                    _20010310016_Kapi kapi = seviye.get(j);
                    System.out.print((j + 1) + ". Kapý ");
                    if (kapi.tur.equals("DEÐÝL")) {
                        System.out.println("giriþi: " + kapi.girisler.get(0));
                    } else {
                        System.out.print(kapi.girisler.size() + "-giriþli ve giriþleri: ");
                        for (int k = 0; k < kapi.girisler.size(); k++) {
                            if (k > 0) System.out.print(", ");
                            System.out.print(kapi.girisler.get(k));
                        }
                        System.out.println();
                    }
                }
                System.out.println();
            }

            Scanner scanner = new Scanner(System.in);
            for (String degisken : degiskenler) {
                System.out.println(degisken + " deðiþkenin deðerini giriniz:");
                devre.degiskenler.put(degisken, scanner.nextInt() == 1);
            }

            devre.calistir();
            boolean sonuc = devre.araDegerler.get(devre.seviyeler.get(devre.seviyeler.size() - 1).get(0).tur + 
                          (devre.seviyeler.size() - 1) + "0");
            System.out.println("Devrenin Sonucu: " + (sonuc ? "1" : "0"));

        } catch (IOException e) {
            System.out.println("boole.txt dosyasý bulunamadý.");
        }
    }
}