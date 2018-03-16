import java.util.Collections;
import java.util.PriorityQueue;

public class RunSimulation {

	public static void main(String[] args) {
		double medianA = 0.0;
		double medianB = 0.0;
		double totalA = 0.0;
		double totalB = 0.0;
		
		//Use minHeap to maintain top 10% return 
		PriorityQueue<Double> topTenPercentA = new PriorityQueue<Double>(1000);
		PriorityQueue<Double> topTenPercentB = new PriorityQueue<Double>(1000);

		//Use maxHeap to maintain last 10% return
		PriorityQueue<Double> lastTenPercentA = new PriorityQueue<Double>(1000, Collections.reverseOrder());
		PriorityQueue<Double> lastTenPercentB = new PriorityQueue<Double>(1000, Collections.reverseOrder());

		//Use MedianTracker to track median of the results 
		MedianTracker mtA = new MedianTracker();
		MedianTracker mtB = new MedianTracker();
	
		MonteCarloSimulation sA = new MonteCarloSimulation();
		MonteCarloSimulation sB = new MonteCarloSimulation();
		
		for (int i = 0; i < 10000; i++) {
			totalA = sA.runMultiYears(20, 100000.0, 9.4324, 15.675);
			//System.out.println(totalA);
			mtA.read(totalA);
			medianA = mtA.median();	
			
			//Update top 10% for portfolio A
			if (topTenPercentA.size() < 1000) {
				topTenPercentA.offer(totalA);
			} else if (totalA > (Double)topTenPercentA.peek()) {
				topTenPercentA.poll();
				topTenPercentA.offer((Double)totalA);
			}
			
			//Update last 10% for portfolio B
			if (lastTenPercentA.size() < 1000) {
				lastTenPercentA.offer(totalA);
			} else if ( totalA < (Double)lastTenPercentA.peek()) {
				lastTenPercentA.poll();
				lastTenPercentA.offer((Double)totalA);
			}
			
			totalB = sB.runMultiYears(20, 100000.0, 6.189, 6.3438);
			mtB.read(totalB);
			medianB = mtB.median();
			
			//Update top 10% for portfolio A
			if (topTenPercentB.size() < 1000) {
				topTenPercentB.offer(totalB);
			} else if (totalB > (Double)topTenPercentB.peek()) {
				topTenPercentB.poll();
				topTenPercentB.offer((Double)totalB);
			}
			
			//Update top 10% for portfolio A
			if (lastTenPercentB.size() < 1000) {
				lastTenPercentB.offer(totalB);
			} else if (totalB < (Double)lastTenPercentB.peek()) {
				lastTenPercentB.poll();
				lastTenPercentB.offer((Double)totalB);
			}
		}
	
		System.out.println("Median A : " + medianA);
		System.out.println("Top 10% A : " + topTenPercentA.poll());
		System.out.println("Last 10% A : " + lastTenPercentA.poll());
		
		System.out.println("Median B : " + medianB);
		System.out.println("Top 10% B : " + topTenPercentB.poll());
		System.out.println("Last 10% B : " + lastTenPercentB.poll());
	}

}
