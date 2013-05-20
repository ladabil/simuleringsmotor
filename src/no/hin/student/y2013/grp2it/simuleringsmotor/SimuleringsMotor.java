package no.hin.student.y2013.grp2it.simuleringsmotor;

public class SimuleringsMotor extends SimulatorBase {
	static SimuleringsMotor simMotor = null;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		simMotor = new SimuleringsMotor();
		simMotor.doRun();
	}
	
	public void doRun()
	{
		System.out.println("Running");
		
		System.out.print("Sleeping");
		for ( int i=0; i<100; i++)
		{
			try {
				Thread.sleep(100);
				System.out.print(".");
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println(".");
		
		System.out.println("Died");
	}

}
