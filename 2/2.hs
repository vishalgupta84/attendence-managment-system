import Data.List
import Data.Maybe
import System.IO
codes = [4719,1234,2222,1111] --list for food item code
quantities = [10,10,10,10]    --list for initial food quantitis available 
price = [121,250,300,170]     --list for item price
name=["Fish Fingers","Chicken Lollypop","Chili Mushroom","Paneer Pakora"] --list for item name
--printHelper function declaration
printHelper::[Int]->[Int]->[Int]->[[Char]]->Int->Int->IO()
--printHelper base function when user entered food item storing list is empty
--print total price here
printHelper [] [] [] [] total index = do
 putStrLn "\n----------------------------------------------------\n"
 let line = "total  **************************************\t" ++ show total
 putStrLn line
--printHelper function to print user entered food item one by one along with price and quantity
--codeHead current food item code ,codeTail rest of item code
--quantityHead current food item quantity ordered by customer ,quanityTail rest of the food quanity
--price .price of item multiplied by quantity ordered by customer
--name contains name of food item orderd by customer
printHelper (codeHead:codeTail) (quantityHead:quantityTail) (priceHead:priceTail) (nameHead:nameTail) total index = do
 if quantityHead > 0
  then do
   let line = show index ++ ".  " ++ show codeHead ++ "    " ++show nameHead ++"\t"++show priceHead ++ "\t"  ++show quantityHead ++ "\t" ++ show priceHead 
   putStrLn line
   printHelper codeTail quantityTail  priceTail nameTail (total+priceHead) (index+1)
 else
  printHelper codeTail quantityTail priceTail nameTail (total+priceHead) (index+1)

--helper function to get user input
--helper function decalartion
helper::[Int]->[Int]->[Int]->[Int]->[Int]->[[Char]]->Int->IO()

--usercode is used to store food item code entered by customer
--userQuantity is used to store food item quantity ordered by customer
--userAvailable is used to store if food item is available or not
--userPrice is used to store price of item multiplied by quanity ordered by customer
--allQuanity is used to store current quantity of all food
--userName is used to store name of item ordered by customer  
helper userCode userQuantity userAvailable userPrice allQuantity userName totalItem = do
 putStrLn "Enter code"
 inputCode <- readLn::IO Int
 let flag = elem inputCode codes
 if flag == True
  then do
   let idx = fromJust $elemIndex inputCode codes
   let itemName = (name!!idx)
   let line = "Item is " ++ show itemName
   putStrLn line
   putStrLn "Enter quantity"
   quantity <- readLn::IO Int
   let availableNow = (allQuantity!!idx) - quantity 
   let quantityNow = take idx allQuantity ++ [availableNow] ++ drop (idx+1) allQuantity
   if availableNow > 0
    then do
     let itemPrice = (price !! idx)*quantity
     putStrLn "More Items? 1 for Yes or 2 for No"
     option <- readLn::IO Int
     let totalItems = totalItem + 1
     let temp = elem inputCode userCode
     if temp ==  True
      then do
       let index = fromJust $elemIndex inputCode userCode
       let q = (userQuantity !! index ) + quantity
       let p = (price!!index) * q
       let newUserQuantity = take index userQuantity ++ [q] ++ drop (index+1) userQuantity
       let newUserPrice = take index userPrice ++ [p] ++drop (index+1) userPrice
       if option == 1
        then do
         helper (userCode) (newUserQuantity) (userAvailable) (newUserPrice) quantityNow (userName) (totalItems)
       else do
        if totalItems > 0
         then do
          putStrLn "\n--------------------------------------------------------------------------------------------------\n"
          putStrLn "\n\t\tALCHERINGA 2018, STALL 14: TANGO FAST FOOD CENTER\n"
          printHelper (userCode) (newUserQuantity) (newUserPrice) (userName) 0 1
        else putStrLn "\n"
     else do
       if option == 1
        then do
         helper (userCode++[inputCode]) (userQuantity++[quantity]) (1:userAvailable) (userPrice++[itemPrice]) quantityNow (userName++[itemName]) (totalItems)
       else do
        if totalItems > 0
         then do
          putStrLn "\n--------------------------------------------------------------------------------------------------\n"
          putStrLn "\n\t\tALCHERINGA 2018, STALL 14: TANGO FAST FOOD CENTER\n"
          printHelper (userCode++[inputCode]) (userQuantity++[quantity]) (userPrice++[itemPrice]) (userName++[itemName]) 0 1
        else putStrLn "\n"
   else do
    let line = "This much quantity of " ++ show itemName ++ " is not available"
    putStrLn line
    putStrLn "More Items ? 1 for Yes or 2 for No"
    option <- readLn ::IO Int 
    if option == 1
     then do
      helper userCode userQuantity userAvailable userPrice allQuantity userName totalItem
    else do
     if totalItem > 0
      then do
       putStrLn "\n--------------------------------------------------------------------------------------------------\n"
       putStrLn "\n\t\tALCHERINGA 2018, STALL 14: TANGO FAST FOOD CENTER\n"
       printHelper userCode userQuantity userPrice userName 0 1
     else putStrLn "\n"
    -- print "hi"
 else do
  putStrLn "**WRONG CODE, NO ITEM FOUND**"
  putStrLn "More Items? 1 for Yes or 2 for No"
  option <- readLn::IO Int
  if option == 1
   then do
    helper (userCode) (userQuantity) (userAvailable) (userPrice) allQuantity (userName) totalItem
  else do
   if totalItem > 0
    then do
     putStrLn "\n-----------------------------------------------------------------------------------------------------\n"
     putStrLn "\n\t\tALCHERINGA 2018, STALL 14: TANGO FAST FOOD CENTER\n"
     printHelper (userCode) (userQuantity) (userPrice) (userName) 0 1
   else putStrLn "\n"
main = do
 helper [] [] [] [] quantities [] 0