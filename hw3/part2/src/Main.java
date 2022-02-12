public class Main {

  public static void main(String[] args) {
    EmailComponent logan = new Email("Logan Roy", "loganroy@waystar.com");
    EmailComponent shiv = new Email("Siobhan Roy", "siobhanroy@waystar.com");
    EmailComponent ken = new Email("Kendall Roy", "kendallroy@waystar.com");
    EmailComponent roman = new Email("Roman Roy", "romanroy@waystar.com");
    EmailComponent gerri = new Email("Gerri Kelman", "gerrikelman@waystar.com");
    EmailComponent tom = new Email("Tom", "tomwambsgans@waystar.com");

    EmailComponent roys = new EmailGroup("royfamily@waystarroyco.com");
    EmailComponent roySiblings = new EmailGroup("roysiblings@waystarroyco.com");
    roySiblings.add(shiv);
    roySiblings.add(ken);
    roySiblings.add(roman);
    roys.add(logan);
    roys.add(roySiblings);
    EmailComponent waystar = new EmailGroup("board@waystarroyco.com");
    waystar.add(gerri);
    waystar.add(tom);
    waystar.add(roys);

    System.out.println("==== TEST: print method called on an email =====");
    ken.print();
    System.out.println("==== TEST: print method called on an email group where elements are an email and an email group =====");
    roys.print();
    System.out.println("==== TEST: print method called on an email group where elements are emails and nested email groups =====");
    waystar.print();
  }
}
