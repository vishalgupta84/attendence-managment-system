import Data.Char (ord)
import Data.Char (chr)
--decrpt function definition
add sum = do
 if sum < 194 
  then add (sum+26)
 else sum
decrypt::[Char]->[Char]->Int->Int->IO()
--base case for decrpyt function if plain text is empty 
encrypt [] keyText keyLength index=return ()
--base case if plain text contains only one character
decrypt[head] keyText keyLength index=do
	let key=(keyText!!index)
	let keyVal=(ord key)
	let headVal=(ord head)
	let textVal = add(headVal) - keyVal ---call function and bring to range
	-- let rem=headVal-96
	-- let textVal=rem + 200 - keyVal		--bring the character sum in the range of 194 and 244 	
	-- print textVal-26 				--194 is lowest sum we can obtain by adding plain text and key character
	if headVal > 64 && headVal < 91		--check if cypher text contains capital character
		then do
			let ch=(chr headVal)
			putChar ch
			putStrLn "\n"
	else if headVal > 96 && headVal < 123 --check if cypher text current character is small if yes 
		then do
			if textVal > 122				--check if textval value we obtained is more than 122
				then do
					let ch=(chr (textVal-26))	--just print corresponding of textval we obtained above
					putChar ch
					putStrLn "\n"
			else if textVal < 97
			 then do
			  let ch=(chr (textVal + 26))
			  -- print textVal
			  putChar ch
			  putStrLn "\n"
			else do
			 let ch = (chr textVal)
			 putChar ch
			 putStrLn "\n"
	else do
		if head == '*'
			then do 
				putChar '0'
				putStrLn "\n"
		else if head == '`'
			then do 
				putChar '1'
				putStrLn "\n"
		else if head == '~'
			then do 
				putChar '2'
				putStrLn "\n"
		else if head == '!'
			then do 
				putChar '3'
				putStrLn "\n"
		else if head == '@'
			then do 
				putChar '4'
				putStrLn "\n"
		else if head == '#'
			then do 
			 	putChar '5'
				putStrLn "\n"
		else if head == '$'
			then do 
				putChar '6'
				putStrLn "\n"
		else if head == '%'
			then do 
				putChar '7'
				putStrLn "\n"
		else if head == '^'
			then do 
				putChar '8'
				putStrLn "\n"
		else do
			putChar '9'
			putStrLn "\n"
--decrypt function
decrypt(head:tail) keyText keyLength index=do
	let key=(keyText!!index)
	let keyVal=(ord key)
	let headVal=(ord head)
	-- let rem=headVal-96
	-- let textVal=rem + 200 - keyVal
	let retunVal = add(headVal)
	let textVal = retunVal - keyVal
	-- print retunVal
	-- print textVal
	-- print textVal
	if headVal > 64 && headVal < 91		--check if current chracter in cypher text is capital
		then do
			let ch=(chr headVal)
			putChar ch
			decrypt tail keyText keyLength (mod index keyLength)
	else if headVal > 96 && headVal < 123 --check if current chracter in cypher text is small
		then do
			if textVal > 122			--check if textval we obtain above is more than 122
				then do
					let ch=(chr (textVal-26))	--if yes subtract 26 and print correponding character
					putChar ch
			else if textVal < 97 
			 then do
			  let ch = (chr (textVal+26))
			  putChar ch
			else do
				let ch=(chr textVal)
				putChar ch
			decrypt tail keyText keyLength (mod (index+1) keyLength)
	else do
		if head == '*'
			then do putChar '0'
		else if head == '`'
			then do putChar '1'
		else if head == '~'
			then do putChar '2'
		else if head == '!'
			then do putChar '3'
		else if head == '@'
			then do putChar '4'
		else if head == '#'
			then do putChar '5'
		else if head == '$'
			then do putChar '6'
		else if head == '%'
			then do putChar '7'
		else if head == '^'
			then do putChar '8'
		else do
			putChar '9'
		-- print head
		decrypt tail keyText keyLength (mod index keyLength)

main = do
	putStrLn "Enter cypher text"
	cypherText <- getLine
	putStrLn "Enter key"
	keyText <- getLine
	-- putStrLn (plainText ++ " " ++ keyText)
	let keyLength = length keyText
	-- print keyLength
	putStrLn "\ncorresponding plain text is"
	decrypt cypherText keyText keyLength 0