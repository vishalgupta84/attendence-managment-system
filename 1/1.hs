-- this function is for finding union of two sets
union::[Int]->[Int]->[Int]
union [] y = (unionSet)
 where
  unionSet = y
union (head:tail) second=do
 let flag = elem head second
 if flag == True
  then union tail second
 else union tail (head:second)

-- this function is for finding intersection of two sets

intersection::[Int]->[Int]->[Int]->[Int]
intersection [] y newSet= (intersectionSet)
 where
  intersectionSet=newSet
intersection (head:tail) second newSet= do
 let flag = elem head second
 if flag == True
  then intersection tail second (head:newSet)
 else intersection tail second newSet

-- this function is for finding difference of two sets

subtraction::[Int]->[Int]->[Int]->[Int]
subtraction [] y newSet = (differenceSet)
 where 
  differenceSet = newSet
subtraction (head:tail) second newSet = do
 let flag = elem head second
 if flag == True
  then subtraction tail second newSet
 else subtraction tail second (head:newSet)

-- this function is main helper function which handles query

helper::[Int]->[Int]->IO()
helper first second = do
 putStrLn "\nYour sets are"
 print first 
 print second
 putStrLn "Enter option\n(1) To insert into list\n(2) To check if Set is empty\n(3) To find union\n(4) To find Intersection\n(5) To subtract\nAny other number to exit"
 option <- readLn::IO Int
 if option == 1
  then do
   putStrLn "Enter Number to be inserted"
   number <- readLn::IO Int
   putStrLn "which list you want to insert to"
   whichSet <- readLn::IO Int
   if whichSet == 1
    then do
     let flag = elem number first
     if flag == True
      then do
       helper first second
     else do
      helper (first++[number]) second 
   else do
    let flag = elem number second
    if flag == True
     then do
      helper first second
    else helper first (second++[number])
 else if option == 2
  then do
   putStrLn "which set you want to check if empty"
   whichSet <- readLn::IO Int
   if whichSet == 1
    then do
   	 if first == []
   	  then do
   	   putStrLn "\nfirst set is empty"
   	 else putStrLn "\nfirst set is not empty"
   	 helper first second
   else do
    if second == []
     then putStrLn "\nsecond set is empty"
    else putStrLn "\nsecond set is not empty"
    helper first second
 else if option == 3 
  then do
  	let unionSet=union first second
  	putStrLn "\nunion of above two sets is"
  	print unionSet
  	helper first second
 else if option == 4
  then do
   let intersectionSet = intersection first second []
   putStrLn "\nIntersection of above two sets is"
   print intersectionSet
   helper first second
 else if option == 5
  then do
   putStrLn "Enter 1 for (set1-set2) or 2 for (set2-set1)"
   t <- readLn::IO Int
   if t == 1
   	then do
   	 let difference = subtraction first second []
   	 putStrLn "Difference is "
   	 print difference
     -- putStrLn "\nDifference of first set from second Set is"
     -- print difference
   else do
   	let difference = subtraction second first []
   	putStrLn "Difference is"
   	print difference
    -- putStrLn "\nDifference of second set from first Set is"
    -- print difference
   helper first second
 else do
  putStrLn "program finished"
--main function 
main = do
 let first=[1,2,3,4]
 let second=[]
 helper first second
