# DeBuff

Algoritam - Luka List\
Design - Tin Prvcic


U glavnom izborniku korisniku je ponuđeno odabrati jednu od ponuđenih 13 kiselina označenih kemijskim formulama i razvrstanih po broju protona te dalje raditi s odabranom ili odabrati ručni unos podataka o kiselini koja možda nije ponuđena. Ponuđene kiseline su s pažnjom prepoznate kao najčešće u zadacima iz analitičke kemije.

Nakon što se u glavnom izborniku odabrala neka kiselina, otvara se zaslon glavne aktivnosti MainAcitivity. Korisnika se zatim traži unos podataka u jedno okno, iznad kojeg piše naziv trenutnog unosa, koji su redom:
•	„Sveukupna koncentracija kiseline“ (podrazumijeva zbroj koncentracija same kiseline i njezinih aniona te se pri stalnom volumenu ne mijenja)
•	„Sveukupna koncentracija kationa“ (podrazumijeva se jednovalentnih i idealnih, tj. nimalo kiselih)
•	„Volumen otopine“

Svaki se parametar pritiskom na gumb „next“ vidljiv na slici 7 provjerava (ispravnost i smislenost) i sprema u zasebnu varijablu. Nakon spremanja mijenja se naslov unosa i po potrebi mjerna jedinica pored EditText objekta. Na slici 7 prikazan je unos sveukupne koncentracije kiseline [M = mol/L]. Svaki unos ima svoj InputType (vrstu unosa) pa tako npr. nije moguće unijeti negativnu koncentraciju kiseline ili decimalni broj kiselih protona, ali je moguće unijeti negativnu koncentraciju pozitivnog naboja.

Svi parametri koje aplikacija iskazuje rezultat su rješavanja polinoma prema formuli niže.

![formula](https://drive.google.com/uc?export=view&id=1L_WWSwSODXoy7QB-HJzQmmNTuabdyvgr)
