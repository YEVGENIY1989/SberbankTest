package sber.test;

import xmlManager.CreateBankXml;
import xmlManager.ReadBankXml;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {

        ReadBankXml readBankXml = new ReadBankXml("bank.xml");
        BankBalance bankBalance = new BankBalance(readBankXml.getPersons(), readBankXml.getWallet());
        CreateBankXml bankXml = new CreateBankXml(bankBalance.getPersons(), bankBalance.getMinAppendFromBank());

    }
}
