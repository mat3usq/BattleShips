# "Battleships"

## Główne Założenia
- Cel projektu: Przeniesienie klasycznej gry planszowej "Battleships" do formy interaktywnej w aplikacji okienkowej z użyciem interfejsu graficznego i biblioteki Swing.
- Istota aplikacji: Umożliwienie graczom planowania taktyki rozmieszczania statków oraz skutecznego strzelania w celu zniszczenia floty przeciwnika przy jednoczesnym wykorzystaniu prostego i intuicyjnego interfejsu tekstowego.

## Opis Funkcjonalności
### Gra przeciwko Komputerowi
- Gracze mają opcję rozpoczęcia rozgrywki przeciwko komputerowi zaprogramowanemu przez programistę.
- Możliwość interaktywnego rozmieszczania statków na planszy przed rozpoczęciem rozgrywki.
- Komputer prowadzi swoją flotę, a gracz musi zastosować swoje umiejętności taktyczne, aby go pokonać.
- Po każdym strzale gra informuje gracza o wyniku.
- Gracz, który jako pierwszy zatopi wszystkie statki przeciwnika, zostaje uznany za zwycięzcę.

### Symulacja Gry Między Komputerami
- Możliwość symulacji rozgrywki między dwoma komputerami.

### Zasady Gry
- Dostęp do zasad gry w trakcie rozgrywki, umożliwiający łatwe zrozumienie reguł.

### Zakupy w Sklepie
- Możliwość zdobycia specjalnych statków lub barier poprzez zakupy w wirtualnym sklepie.

### Ranking
- System rankingowy pozwalający śledzić postępy i porównywać umiejętności z innymi graczami.

## Interesujące Zagadnienia Projektowe

### Wykorzystanie biblioteki Swing
- Prosta integracja biblioteki Swing do obsługi interfejsu graficznego, co pozwala na interaktywność w grze oraz zapewnia czytelność i estetykę.
- Biblioteka została wykorzystana do wyświetlania wszystkich widoków, oferując funkcje umożliwiające sprawną prezentację elementów graficznych.

### Architektura MVC (Model-View-Controller)
- Uproszczenie procesu zmiany lub dodawania nowych strategii prezentacji, nie zakłócając podstawowej logiki aplikacji.
- Dzięki zastosowaniu architektury MVC, dodanie nowych widoków w przyszłości jest znacznie ułatwione.
