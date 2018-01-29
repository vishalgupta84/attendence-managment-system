import Data.Char (ord)
import Data.Char (chr) 	
--function defination
subtractToRange sum = do
 if sum > 122
  then subtractToRange(sum-26)
 else sum
encrypt::[Char]->[Char]->Int->Int->IO()
--base case if plain text is empty return nothing
encrypt [] keyText keyLength index=return ()
--base case if plain text is of length 1
encrypt[last] keyText keyLength index=do		
	let key = (keyText!!index) -- character at current index in keyText
	let keyVal=(ord key)       --corresponding ascii value 
	let headVal=(ord last)		--ascii value of current character of plain text
	--bring the sum in the range of 97 to 122.small English alphabet ascii range 
	let sum = subtractToRange (keyVal+headVal)
	if headVal > 64 && headVal< 91 	--check if current character of plain text is capital 
		then do						--if yes print just the character no modification required
			let ch=(chr headVal)
			putChar ch
			putStrLn "\n"
	else if headVal > 96 && headVal < 123 --check if current character of plain text is small
		then do 							--if yes
			let ch=(chr sum)				--print the character correponsing to 'sum' we calculated above
			putChar ch
			putStrLn ("\n")
	else do	
		if headVal == 48				--check if character is 0 if yes print *
			then do 
				putChar '*'
				putStrLn ("\n")
		else if headVal == 49			--check if character is 1 if yes print `
			then do 
				putChar '`'
				putStrLn ("\n")
		else if headVal == 50			--check if character is 2 if yes print ~
			then do 
				putChar '~'
				putStrLn ("\n")
		else if headVal == 51			--check if character is 3 if yes print !
			then do 
				putChar '!'
				putStrLn ("\n")
		else if headVal == 52			--check if character us 4 if yes print @
			then do 
				putChar '@'
				putStrLn ("\n")
		else if headVal == 53			--check if character is 5 if yes print #
			then do 
				putChar '#'
				putStrLn ("\n")
		else if headVal == 54			--check if character is 6 if yes print $
			then do 
				putChar '$'
				putStrLn ("\n")
		else if headVal == 55			--check if character is 7 if yes print %
			then do 
				putChar '%'
				putStrLn ("\n")
		else if headVal == 56			--check if character is 8 if yes print ^
			then do 
				putChar '^'
				putStrLn ("\n")
		else do 
			putChar '&'					--check if character is 9 if yes print &
			putStrLn ("\n")

--function for processing plain text and generating correponding cypher text recursively
--head ----current plain text character
--index --current index of keyText
encrypt(head:tail) keyText keyLength index=do
	let key = (keyText!!index)
	let keyVal=(ord key)
	let headVal=(ord head)
	
	let sum = subtractToRange (keyVal+headVal) --sum the key value and head value and bring it into range of 97 and 122
	if headVal > 64 && headVal< 91		--check if head is capital character
		then do 						--if yes simply print head
			let ch=(chr headVal)
			putChar ch
			encrypt tail keyText keyLength (mod index keyLength)
	else if headVal > 96 && headVal < 123 --check if head is small alphabet
		then do 
			let ch=(chr sum)			--if yes print character corresponding to 'sum' we obtained above
			putChar ch
			-- print sum
			encrypt tail keyText keyLength (mod (index+1) keyLength)
	else do
		if headVal == 48
			then do putChar '*'
		else if headVal == 49
			then do putChar '`'
		else if headVal == 50
			then do putChar '~'
		else if headVal == 51
			then do putChar '!'
		else if headVal == 52
			then do putChar '@'
		else if headVal == 53
			then do putChar '#'
		else if headVal == 54
			then do putChar '$'
		else if headVal == 55
			then do putChar '%'
		else if headVal == 56
			then do putChar '^'
		else putChar '&'	
		-- print head
		encrypt tail keyText keyLength (mod index keyLength)


--main function to get plain and key text from user 
main = do
	putStrLn "Enter plain text"
	plainText <- getLine
	putStrLn "Enter key"
	keyText <- getLine
	-- putStrLn (plainText ++ " " ++ keyText)
	let keyLength = length keyText
	-- print keyLength
	putStrLn "\ncorresponding cypher text is"
	encrypt plainText keyText keyLength 0		--encrypt text using key call function with index 0