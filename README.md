# Assignment 2
In this assignment, you are asked to
  - implement a new transaction - **UpdateItemPriceTxn**, which will randomly select some items and update their price, in the benchmark project.
  - implement a JDBC version of our testbed loader.
  - modify `org.vanilladb.bench.StatisticMgr` in order to make it produce an additional report with more information.

We will run your code to make sure that all the following requirements meet, so please run it yourself before submission.

## Steps
To complete this assignment, you need to

1. Fork the Assignment 2 project
2. Trace the benchmark project code yourself
3. Implement the **UpdateItemPriceTxn** using JDBC and stored procedures
4. Modify the RTE so that you can control the ratio between **ReadItemTxn** and **UpdateItemPriceTxn**. (You can now compare the performance difference between different read-only / read-write transaction ratio. For example, 40% **ReadItemTxn** and 60% **UpdateItemPriceTxn**)
5. Modify `org.vanilladb.bench.StatisticMgr`.
6. Implement a `JdbcJob` for our testbed loader.
7. Load the testbed, run a few experiments and write a report

## Loading The Testbed

In the assignment 2 project, the SchemaBuilder and TestbedLoader are already provided. It should be noted that there are **only stored procedure implementations**. Therefore, you can only populate the data with stored procedure mode. Since it is unconvinent, we ask you to write a JDBC implemention.

When you load the testbed, it will create an **item** table and populate 100000 items by default. You can change how many itmes to be populated in `vanillabench.properties`.

## UpdateItemPriceTxn
You should implement a new transaction - **UpdateItemPriceTxn**, which will randomly select some items and update their price values. The detailed information of this transaction is described as following:

- Prepare parameters
  - Randomly pick 10 item ids
  - Randomly generate 10 new price values
- Executes SQLs
  - `SELECT` the names and prices of these 10 items
  - `UPDATE` the prices of the items using the values you generated on the client side

We have implemented a read-only transaction called **ReadItem** as a reference. Please do not modify the code for that transaction such as `As2ReadItemJob`, `As2ReadItemProc` and `As2ReadItemProcParamHelper`. You should create new classes for yours.

In addtition, we have some requirements:
- Please add a property named `WRITE_TX_RATE` to control how much your transaction will be generated.
- Do not generate parameters in either stored procedres or JDBC jobs.

**Note**: If you encounter `LockAbortException` during your experiments after you implement **UpdateItemPriceTxn**. Please refer to [this article](https://shwu10.cs.nthu.edu.tw/courses-databases-2018-spring/FAQ/blob/master/Lock_Abort_Exception_in_Benchmark.md) in FAQ repository.

## StatisticMgr

After running the benchmarker, it will only produce a report with the following information for now:

```
# of txns (including aborts) during benchmark period: 33678
Details of transactions:
READ_ITEM: 2 ms
READ_ITEM: 3 ms
...
READ_ITEM: 2 ms
READ_ITEM: 2 ms

READ_ITEM 33559 avg latency: 2 ms
Total 33678 Aborted 119 Commited 33559 avg Commited latency: 3 ms
```

As you can see, it only summarizes the throughput (txs/min) and the average latency (ms). However, we usually need more information in order to get deep insight into the experiment. Hence, you are required to modify `StatisticMgr` to make it produce **another report** with average, minimum, maximum, 25th, median, 75th latency along with throughput in every 5 seconds.

Here is an example of the reports we require:

```
time(sec), throughput(txs), avg_latency(ms), min(ms), max(ms), 25th_lat(ms), median_lat(ms), 75th_lat(ms)
65,1024,40,10,120,23,43,87
70,1051,40,11,130,24,42,85
75,1031,40,12,122,23,46,94
80,1100,39,10,123,22,41,83
...
```

The name of the reports should be `[Year][Month][Day]-[Hour][Minute][Sec].csv`. E.g. `20170222-160720.csv`

## The Report
- How you implement the transaction using JDBC and stored procedures briefly. Please do not paste your entire code here.
- A screenshot of any CSV report after you modify the `StatisticMgr`
- Experiements
	- Your experiement enviornment including (a list of your hardware components, the operating system)
		- e.g. Intel Core i5-3470 CPU @ 3.2GHz, 16 GB RAM, 128 GB SSD, CentOS 7
	- The performance (e.g. txs/min) comparison between
		- JDBC and stored procedures
		- different ratio (**at least 3 different ratio**) of **ReadItemTxn** and **UpdateItemPriceTxn**
			- e.g. 100% ReadItemTxn and 0% UpdateItemPriceTxn
			- e.g. 50% ReadItemTxn and 50% UpdateItemPriceTxn
		- (optional) other adjustable parameters
	- **The analysis and explanation for the above experiements**
	- Note: If you are using Windows, you should **turn off** disk write cache feature for correctness. You can find more information [here](https://shwu10.cs.nthu.edu.tw/courses-cloud-databases-2017-spring/FAQ/blob/master/Windows_Disk_Write_Cache.md).
- Anything worth to be mentioned

There is no strict limitation to the length of your report. Generally, a 2~3 pages report with some figures and tables is fine. **Remember to include all the group members' student IDs in your report.**

## Submission

The procedure of submission is as following:

1. Fork our [Assignment 2](https://shwu10.cs.nthu.edu.tw/courses-databases-2018-spring/db18-assignment-2) on GitLab
2. Clone the repository you forked
3. **Set your repository to 'private'**
4. Finish your work and write a report
5. Commit your work, push to GitLab and then open a merge request to submit. The repository should contain
	- *[Project directories]*
	- *[Team Number]*_assignment2_report.pdf (e.g. team1_assignment2_reprot.pdf)

    Note: Each team only need one submission.


## No Plagiarism Will Be Tolerated

If we find you copy someone’s code, you will get **0 point** for this assignment


## Hints

1. We have already implemented a stored procedure version and a JDBC version for **ReadItem**. You can find them in the following packages:
	- JDBC => `org.vanilladb.bench.as2.rte.jdbc.As2ReadItemJob`
	- Stored Procedure => `org.vanilladb.bench.server.procedure.as2.As2ReadItemProc`

2. If you encounter any problem, take a look our [FAQ repository](https://shwu10.cs.nthu.edu.tw/courses-databases-2018-spring/FAQ) first.


## Deadline

Sumbit your work before **2018/04/03 (Tue.) 23:59:59**.

No late submission will be accepted.
