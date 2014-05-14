// HelloWorldAspect.java
public aspect HelloWorldAspect 
{
    pointcut callSayMessage() : call(public static void HelloWorld.say());
    
    before() : callSayMessage() 
    {
        System.out.println("Good day!");
    } //  before() : callSayMessage() 
    
    after() : callSayMessage() {
        System.out.println("Thank you!");
    } //     after() : callSayMessage()
} // WorldAspect