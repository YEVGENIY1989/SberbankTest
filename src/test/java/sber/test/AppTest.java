package sber.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import dao.Person;
import org.junit.Test;
import xmlManager.CreateBankXml;
import xmlManager.ReadBankXml;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */

    private ReadBankXml readBankXml;


    public AppTest(){

        ReadBankXml readBankXml = new ReadBankXml("bank.xml");
        BankBalance bankBalance = new BankBalance(readBankXml.getPersons(), readBankXml.getWallet());
        CreateBankXml bankXml = new CreateBankXml(bankBalance.getPersons(), bankBalance.getMinAppendFromBank());

    }

    @Test
    public void shouldAnswerWithTrue() {
        assertTrue( true );
    }

    @Test
    public void checkBankWallet(){


        ReadBankXml readBankXml = new ReadBankXml("test/bank.xml");
        BankBalance bankBalance = new BankBalance(readBankXml.getPersons(), readBankXml.getWallet());


        BigDecimal sumAppendFromBank = bankBalance.getPersons().stream().map(Person::getAppendFromBank).reduce(BigDecimal.ZERO, BigDecimal::add);

        assertTrue(sumAppendFromBank.equals(readBankXml.getWallet()));


    }

    @Test
    public void checkBankWallet1(){


        ReadBankXml readBankXml = new ReadBankXml("test/bankTest1.xml");
        BankBalance bankBalance = new BankBalance(readBankXml.getPersons(), readBankXml.getWallet());


        BigDecimal sumAppendFromBank = bankBalance.getPersons().stream().map(Person::getAppendFromBank).reduce(BigDecimal.ZERO, BigDecimal::add);

        assertTrue(sumAppendFromBank.equals(readBankXml.getWallet()));


    }

    @Test
    public void checkMinAppendFromBank(){
        ReadBankXml readBankXml = new ReadBankXml("test/bankTest2.xml");
        BankBalance bankBalance = new BankBalance(readBankXml.getPersons(), readBankXml.getWallet());

        ArrayList<Person> expectedMinAppendPersons = new ArrayList<>();

        Person person1 = new Person("Василий");
        Person person2 = new Person("Иван");
        Person person3 = new Person("Анастасия");

        expectedMinAppendPersons.add(person1);
        expectedMinAppendPersons.add(person2);
        expectedMinAppendPersons.add(person3);

        assertEquals(expectedMinAppendPersons.size(), bankBalance.getMinAppendFromBank().size());
        int i = 0;
        for(Person p : expectedMinAppendPersons){
            String name = bankBalance.getMinAppendFromBank().get(i).getName();
            assertTrue(p.getName().equals(name));
            i++;
        }

    }


}
