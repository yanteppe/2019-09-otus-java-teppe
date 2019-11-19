package ru.otus.hw.atm;

import ru.otus.hw.*;
import ru.otus.hw.nominals.DollarNominal;
import ru.otus.hw.nominals.RubleNominal;

import java.util.*;

import static ru.otus.hw.nominals.DollarNominal.*;
import static ru.otus.hw.nominals.RubleNominal.*;

/**
 * ATM banknote storage container
 */
public class BanknoteContainer {
    private SortedMap<RubleNominal, List> rublesCase = new TreeMap<>();
    private SortedMap<DollarNominal, List> dollarsCase = new TreeMap<>();
    private List<Ruble> rubNominal50 = new ArrayList<>();
    private List<Ruble> rubNominal100 = new ArrayList<>();
    private List<Ruble> rubNominal500 = new ArrayList<>();
    private List<Ruble> rubNominal1000 = new ArrayList<>();
    private List<Ruble> rubNominal5000 = new ArrayList<>();
    private List<Dollar> dollarNominal5 = new ArrayList<>();
    private List<Dollar> dollarNominal10 = new ArrayList<>();
    private List<Dollar> dollarNominal20 = new ArrayList<>();
    private List<Dollar> dollarNominal50 = new ArrayList<>();
    private List<Dollar> dollarNominal100 = new ArrayList<>();
    private int rubleTotal = 0;
    private int dollarTotal = 0;

    /**
     * Fold banknotes in container by banknote type and nominal
     *
     * @param banknote banknote
     */
    void putInContainer(Banknote banknote) {
        if (banknote.getBanknoteType().equals("Ruble")) {
            switch (banknote.getNominalValue()) {
                case 50:
                    rubNominal50.add((Ruble) banknote);
                    rublesCase.put(RUB_50, rubNominal50); break;
                case 100:
                    rubNominal100.add((Ruble) banknote);
                    rublesCase.put(RUB_100, rubNominal100); break;
                case 500:
                    rubNominal500.add((Ruble) banknote);
                    rublesCase.put(RUB_500, rubNominal500); break;
                case 1000:
                    rubNominal1000.add((Ruble) banknote);
                    rublesCase.put(RUB_1000, rubNominal1000); break;
                case 5000:
                    rubNominal5000.add((Ruble) banknote);
                    rublesCase.put(RUB_5000, rubNominal5000); break;
            }
        } else if (banknote.getBanknoteType().equals("Dollar")) {
            switch (banknote.getNominalValue()) {
                case 5:
                    dollarNominal5.add((Dollar) banknote);
                    dollarsCase.put(DOLLAR_5, dollarNominal5); break;
                case 10:
                    dollarNominal10.add((Dollar) banknote);
                    dollarsCase.put(DOLLAR_10, dollarNominal10); break;
                case 20:
                    dollarNominal20.add((Dollar) banknote);
                    dollarsCase.put(DOLLAR_20, dollarNominal20); break;
                case 50:
                    dollarNominal50.add((Dollar) banknote);
                    dollarsCase.put(DOLLAR_50, dollarNominal50); break;
                case 100:
                    dollarNominal100.add((Dollar) banknote);
                    dollarsCase.put(DOLLAR_100, dollarNominal100); break;
            }
        }
    }

    Map getGeneralContainerData() {
        for (RubleNominal nominalKey : rublesCase.keySet()) {
            switch (nominalKey) {
                case RUB_50: rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_50.getNominal(); break;
                case RUB_100: rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_100.getNominal(); break;
                case RUB_500: rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_500.getNominal(); break;
                case RUB_1000: rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_1000.getNominal(); break;
                case RUB_5000: rubleTotal = rubleTotal + rublesCase.get(nominalKey).size() * RUB_5000.getNominal(); break;
            }
        }
        for (DollarNominal nominalKey : dollarsCase.keySet()) {
            switch (nominalKey) {
                case DOLLAR_5: dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_5.getNominal(); break;
                case DOLLAR_10: dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_10.getNominal(); break;
                case DOLLAR_20: dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_20.getNominal(); break;
                case DOLLAR_50: dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_50.getNominal(); break;
                case DOLLAR_100: dollarTotal = dollarTotal + dollarsCase.get(nominalKey).size() * DOLLAR_100.getNominal(); break;
            }
        }
        Map<String, Integer> data = new HashMap<>();
        data.put("rubles", rubleTotal);
        data.put("dollars", dollarTotal);
        return data;
    }
}
