import java.util.Scanner;

class Process {
    int pid, at, bt, ct, tat, wt;
}

public class SJF_NonPreemptive {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter the number of processes: ");
        int n = sc.nextInt();
        Process[] processes = new Process[n];

        for (int i = 0; i < n; i++) {
            processes[i] = new Process();
            processes[i].pid = i + 1;
            System.out.print("Enter Arrival Time and Burst Time for process " + (i + 1) + ": ");
            processes[i].at = sc.nextInt();
            processes[i].bt = sc.nextInt();
        }

        calculateTimes(processes, n);

        sc.close();
    }

    static void calculateTimes(Process[] p, int n) {
        int currentTime = 0, completed = 0;
        boolean[] isCompleted = new boolean[n];
        float totalTAT = 0, totalWT = 0;

        while (completed != n) {
            int minIndex = -1;
            int minBurst = Integer.MAX_VALUE;

            // Select the process with minimum burst time
            for (int i = 0; i < n; i++) {
                if (p[i].at <= currentTime && !isCompleted[i] && p[i].bt < minBurst) {
                    minBurst = p[i].bt;
                    minIndex = i;
                }
            }

            if (minIndex == -1) {
                currentTime++;
            } else {
                currentTime += p[minIndex].bt;
                p[minIndex].ct = currentTime;
                p[minIndex].tat = p[minIndex].ct - p[minIndex].at;
                p[minIndex].wt = p[minIndex].tat - p[minIndex].bt;

                totalTAT += p[minIndex].tat;
                totalWT += p[minIndex].wt;
                isCompleted[minIndex] = true;
                completed++;
            }
        }

        printResults(p, n, totalTAT, totalWT);
    }

    static void printResults(Process[] p, int n, float totalTAT, float totalWT) {
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + p[i].pid + "\t" + p[i].at + "\t" + p[i].bt + "\t" + p[i].ct + "\t" + p[i].tat + "\t" + p[i].wt);
        }
        System.out.println("Average Turnaround Time: " + totalTAT / n);
        System.out.println("Average Waiting Time: " + totalWT / n);
    }
}