# Kino marzeń

### Założenia
- Java 8
- Każda sala ma 5x5 miejsc ponumerowanych indeksami: [1..5][1..5]
- Aplikacja pokazuje repertuary z tygodniowym wyprzedzeniem
- Klient ma 30min do zapłacenia rezerwacji

### Build
<pre><code>mvn package</code></pre>

### Run
<pre><code>java -jar -Dfile.encoding=UTF-8 target\demo-0.0.1.jar</code></pre>
 
### Pokaż listę filmów
<pre><code>curl --location --request GET 'localhost:8080/screenings?moment=2020-03-08T13:00Z'</code></pre>

### Pokaż listę dostępnych miejsc
<pre><code>curl --location --request GET 'localhost:8080/screenings/1/seats'</code></pre>
<br>
Jeśli screening nie istnieje:
<pre><code>curl --location --request GET 'localhost:8080/screenings/-1/seats'</code></pre>

### Rezerwacja miejsc
<pre>
<code>curl --location --request POST 'localhost:8080/reservations'
      --header 'Content-Type: application/json'
      --data-raw '{ 
      	"screeningId": 2,
      	"firstName":"Żania",
      	"lastName":"Ania-Ściga",
      	"reservedSeats": {
      		"12":"CHILD",
      		"25":"ADULT"
      	}
      }'
</code>
</pre>

Pozostawić wolne miejsce pomiędzy zajętymi miejscami
<br>
<pre>
<code>
curl --location --request POST 'localhost:8080/reservations'
--header 'Content-Type: application/json'
--data-raw '{
	"screeningId": 1,
	"firstName":"Chung",
	"lastName":"Tran",
	"reservedSeats": {
		"12":"ADULT"
	}
}'
</code>
</pre>