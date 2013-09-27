BranchAndBound
==============

An implementation of Branch and Bound algoritghmn to solve Multiple Strips Problem(MSP)



Design of the algorithm:

Assume there are K strips and n objects for the MSP. Let N numbers of objects are partitioned into m subsets, which sum of sets: s1, s2, s3….sm. The height of the objects is: h1, h2, h3…..hn.
We are going to build a multi-tree, which each node is one possible subset of the objects set and each path of the tree represent one possible way of placement of object. The level of the tree is equal to the number of trips (one strip in one level). For each strip, there are 2^ (N-P) placements, which P is the sum of objects number in the previous strips in the same path. For example, there are 2 strips and 3 objects. Thus there are 3 levels in the tree. And in the first level of tree, there are 2^ (3-0) =8 placements methods. By using the branching scheme (defined in later part), we can prune out some placements methods, so the remain placements would be smaller than before. After doing this, we know how many nodes (one placement represent one node) in this level. For each node, it will generate its new placements in the next level until all the objects are included.
In this way, the generated tree actually contain all solution space of the one-dimensional MSP, each path is actually one placements method. Compare each path’s Lower Bound (LB), the final output would be minimum {LB1, LB2, LB3……LB number of path}.
Lower Bound:  For each path in the tree, the LB of this path should be maximum {S1, S2……Sm}, where there are |m| nodes in a path. The nodes number is less or equal to the number of strips.
For instance, there are 2 strips, 3 objects: a, b, c, with height ha, hb, hc respectively Thus there are 2 levels of the tree. In the first level, there are totally 2^(3-0)=8 placements:{} ,{a}, {b}, {c},{ab},{bc},{ac},{abc}. After pruning by the branching condition, assume left {bcd}, {ac} in the first level. For each placement left, we can find its son on the next level. Take {ac} as example, there are 2^ (3-2) =2 placements in the level 2 of tree: {b} or { }. Thus it leads a path {ac}-{b}. The LB of this path should be: max {ha+hc, hb}. The LB value of the path may change as the level grows. 
Since there are no more than |k| in a path, it requires at most |k| sets of comparison, it equivalent to find the maximum value of set of values. Thus the time complexity of find each path’s LB would be O (|K|).


Upper bound:
The upper bound should the result of greedy algorithm or some algorithm result else. Since the Upper bound will determine the acceptable value of bound. Thus I defined the calculation bellows:
Upper bound (UP) = [Sum of (height of all objects height h1, h2, h3…hn)/# of the strips]*(100+S %).
S: acceptance degree, the user defined percentage number which reflect the acceptable range around the average value.


Why set up S?

Take an example, when you want to buy a product, the same type of product’s price may vary. But you have limited money to buy. Thus you need to set up a maximum acceptable price before choose the product. Suppose the product of this type is average 10 dollar, you can accept the price no expensive than 10% of the average price. Thus it is 10(1+10%) =11 dollar. Other product which price greater than 11 dollar will not considered by us.
Similarly, we can calculate the average height of each strip. And plus the acceptance degree to gain the Upper Bound for the whole tree. And if the acceptance degree is too large, and there won’t be helpful for us to prune the unnecessary placements methods while constructing the tree. It will cause the tree become much larger. Thus choose S appropriately is some sense to determine the efficient of MSP algorithm. For example, there are 2 strips and 3 objects, we set S=10%, the UP would be equal to 1.1*(h1+h2+h3)/3.
The pseudo code of calculating UP:
For i=1 to n
   Sum+=Height of each objects h1,h2……hn
End for 
   Average= Sum/k    // K is number of strips
       UP=Average*(100+S)   

Thus the time complexity of calculating UP should be O (n).



Separation scheme:
Each sub-problem is a way to putting unsigned numbers into the one empty set. 


Exploration scheme: 
Best first would be better. After getting the potential node in each level, then prune the unnecessary node by using branching scheme, we can gain the nodes in this level. The first one of these nodes is our next exploration node.
For instance, there are 2 strips, 3 objects: a, b, c, with height ha, hb, hc respectively Thus there are 2 levels of the tree. In the first level, there are totally 2^(3-0)=8 placements: {}, {a}, {b}, {c},{ab},{bc},{ac},{abc}. After pruning by the branching scheme condition, assume left {bc},{a} in the first level. {bc} is the next node we are going to explore.


Branching scheme:
We set up a prune condition for eliminating the unnecessary node, which can reduce the workload of constructing the tree (no necessary go through nodes that can not obtain optimal solution in final)
Prune if: |LB-avergae|>|UB-avergae|  
Average: sum of (all objects height)/number of strips


Pseudo code of one dimensional MSP:
       Average = sum of (all objects height)/number of strips;
   For each subset (or strips) si;
      For each way of putting unsigned numbers into si:
         If |LB-Average|<|UB-Average|
          Make a node for this way of signing
          And put it into tree.
         Else
          Prune it.
   For each path of the tree
       Height=min {LB1, LB2, LB3……LB number of path};
// the outer loop repeat number of subset times, that is the number of levels
// the inner loop repeat a lots. If level 0, it repeats 2^n times. And the inner loop is equivalent to the subset sum problem or knapsack problems.

Experiment result (running on duanl core CPU 2.0GHZ +1.5Gb memory): 
Instance 	B&B
No.	Optimal values	Computing times
11	1.32	          22s
12	3.36	          47s
13	5.18	          91s
14	2.09	          114s
15	5.54	          296s
16	3.28	          383s
17	6.17	          521s
18	5.03	          Over 10mins time limit
19	6.20	          Over 10mins time limit
20	7.35	          Over 10mins time limit


Conclusion:
Since we deal with the one-dimensional problem of MSP, which means to ignore the width of the objects in B&B algorithm, so the one dimensional problem is equivalent to the number partition problems. Although we need to construct the tree, but as size of data instance grows, the tree may be grow very huge and inefficient to do the search. In some sense, the Upper Bound calculation determine the whole B&B efficiency, thus we need to carefully determine the acceptable degree S to avoid unnecessary nodes generation while constructing the tree. It also saves space and the computation time.
Since the problem is equivalent to the number partition problem, if the objects well-distributed in each strips, this is the expected case, and the output of the MSP would be close to the average height of the strip. Thus we choose the average height of each strip plus the proper offset may be feasible criteria to apply in solving one-dimensional MSP with B&B. 
