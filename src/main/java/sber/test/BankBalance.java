package sber.test;

import dao.Person;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BankBalance {


    private BigDecimal balanceOnBank;
    private List<Person> persons;
    private List<Person> minAppendFromBank;


    public BankBalance(ArrayList<Person> persons, BigDecimal balanse){


        this.persons = persons;
        balanceOnBank = balanse;
        minAppendFromBank = new ArrayList<>();

        calculateMoneyForPerson();

    }


    // Находим общую сумму денег на счету пользователей, складываем ее с числом счета банка. Получаем значение которое нам нужно распределить между пользователями.
    // Делим это значение на кол-во пользователей для получение коэфициента и затем от него отнимаем значение которое есть у каждого пользователя
    // таким образом мы получим значение которое нужно добавить к каждому пользователю, чтоб у всех на счету стало максимально одинаковое кол-во денег на своих кошельках
    private void calculateMoneyForPerson(){

        BigDecimal sumPersonsWallet = new BigDecimal(0) ;
        BigDecimal koef = new BigDecimal(0);
        koef.setScale(2, BigDecimal.ROUND_CEILING);
        sumPersonsWallet.setScale(2, BigDecimal.ROUND_CEILING);

        sumPersonsWallet = persons.stream().map(Person::getWallet).reduce(BigDecimal.ZERO, BigDecimal::add); // нашли сумму счета пользователей

        koef = sumPersonsWallet.add(balanceOnBank); // сложили с балансом банка
        koef = koef.divide(BigDecimal.valueOf(persons.size()), BigDecimal.ROUND_CEILING); // получили коэфф т.е число которое должно стать у каждого пользователя чтоб у всех пользователей стало максимальное кол-во денег
        BigDecimal sum = new BigDecimal(0);
        sum.setScale(2, BigDecimal.ROUND_CEILING);
        int i = 0;
        for (Person p : persons){

            if(i == persons.size() - 1){
                BigDecimal walletBalance = balanceOnBank.subtract(sum).setScale(2, BigDecimal.ROUND_CEILING);
                p.setAppendFromBank(walletBalance);
                break;
            }

            BigDecimal moneyFromBank = koef.subtract(p.getWallet()).setScale(2,BigDecimal.ROUND_CEILING);
            sum = sum.add(moneyFromBank);
            p.setAppendFromBank(moneyFromBank);

            i++;

        }

        Optional<Person> minAppend = persons.stream().min(Comparator.comparing(Person::getAppendFromBank));
        BigDecimal min = minAppend.get().getAppendFromBank();

        for (Person p : persons){

            if(min.equals(p.getAppendFromBank())){
                minAppendFromBank.add(p);
            }

        }


    }

    public List<Person> getPersons() {
        return persons;
    }

    public List<Person> getMinAppendFromBank() {
        return minAppendFromBank;
    }
}
