% Digitalna obrada i analiza slike - T. Petković - 2026-05-13
% Primjeri iz analize oblika


%% Definiranje testnih slika
% Definiramo veličinu sive slike koju ćemo koristiti kao primjer u ovom kodu.
% U sljedećim blokovima prvo definiramo razne oblike kao što su pravokutnik,
% elipsa, poligon, te poligon s rupom. Svaki blok koda generiranu sliku
% snima u matricu I koja se onda koristi u kodu za analizu pa je aktivna
% slika ona koja odgovara bloku koda kojeg smo zadnjeg pokrenuli.
%
% Napomena: Matlab sprema slike kao matrice, matrice u memoriju sprema po
% stupcima, a kod crtanja slike na ekranu crta matricu sukladno
% matematičkoj konvenciji, odnosno prvi element matrice je u gornjem
% lijevom kutu. Prema tome, prvi indeks elemenata matrice koji indeksira
% stupce ide po osi Y slike, a drugi indeks koji indeksira retke ide po osi
% X slike, te kod prepisivanja formula za obradu slike moramo paziti na
% tu zamjenu koordinata.

szx = 300; % Širina slike.
szy = 200; % Visina slike.

[x, y] = meshgrid(1:szx, 1:szy);
I_prazna = repmat(uint8(0),szy,szx);


%% Testna slika s pravokutnikom
% Kvadrat i pravokutnik čije stranice su poravnate s osima slike crtamo
% izravnim adresiranjem vrijednosti matrice. 

x0 = 100;  y0 = 50;  % Gornji lijevi kut.
dx = 100;  dy = 100;  % Širina i visina slike.

I_pravokutnik = I_prazna;
I_pravokutnik(y0:y0+dy,x0:x0+dx) = uint8(255);

figure;
imshow(I_pravokutnik);
axis on;

I = I_pravokutnik;


%% Testna slika s nakošenom elipsom
% Elipsu crtamo korištenjem logičkog adresiranja matrice.

cx = 150;  cy = 100;  % Centar elipse.
rx = 100;  ry = 50;  % Poluradijusi elipse.
theta = pi/5;  % Kut rotacije.

t1 = ( (x-cx) * cos(theta) + (y-cy) * sin(theta) ).^2 / rx.^2;
t2 = ( (x-cx) * sin(theta) - (y-cy) * cos(theta) ).^2 / ry.^2;
idx_elipsa = 1 > t1 + t2;
I_elipsa = I_prazna;
I_elipsa(idx_elipsa) = uint8(255);

figure;
imshow(I_elipsa);
axis on;

I = I_elipsa;


%% Testna slika s trokutom
% Bilo koji poligon možemo nacrtati korištenjem funkcije poly2mask.

Ax =  50;  Ay =  30;  % Prvi vrh.
Bx = 250;  By = 100;  % Drugi vrh.
Cx = 143;  Cy = 191;  % Treći vrh.

idx_trokut = poly2mask([Ax Bx Cx], [Ay By Cy], szy, szx);
I_trokut = I_prazna;
I_trokut(idx_trokut) = uint8(255);

figure;
imshow(I_trokut);
axis on;

I = I_trokut;


%% Testna slika objekta s rupom (trokut u trokutu)
% Zadnja slika sadrži složeni objekt s rupom.

A1x =  50;  A1y =  30;  % Prvi vrh.
B1x = 250;  B1y = 100;  % Drugi vrh.
C1x = 143;  C1y = 191;  % Treći vrh.

A2x = 120;  A2y =  80;  % Prvi vrh.
B2x = 200;  B2y = 110;  % Drugi vrh.
C2x = 150;  C2y = 140;  % Treći vrh.

idx_trokut_1 = poly2mask([A1x B1x C1x], [A1y B1y C1y], szy, szx);
idx_trokut_2 = poly2mask([A2x B2x C2x], [A2y B2y C2y], szy, szx);
I_tut = I_prazna;
I_tut(idx_trokut_1) = uint8(255);
I_tut(idx_trokut_2) = uint8(0);

figure;
imshow(I_tut);
axis on;

%I = I_tut;


%% Ekstrakcija objekta i praćenje granice
% Odabiremo objekt na slici te pratimo njegovu vanjsku granicu.
% Podrazumijevamo 8-povezanost za objekt, te 4-povezanost za pozadinu.

% Labeliraj objekte na slici.
[L, n] = bwlabel(I, 8);

% Izdvoji prvi objekt.
B = bwboundaries(1==L, 8);

% Nacrtaj objekt i izdvojenu granicu.
figure;
imagesc(I);
colormap(gray(256));
hold on;
plot(B{1}(:,2), B{1}(:,1), 'r');
hold off;
axis on;
grid;


%% Fourierova transformacija granice
% Diskretiziranu granicu objekta možemo pretvoriti u kompleksnu funkciju te
% zatim izračunati Fourierovu transformaciju te funkcije. Fourierovom
% transformacijom granicu opisujemo korištenjem kompleksnih eksponencijala
% koje su bazne funkcije transformacije i koje možemo interpretirati: nulta
% komponenta definira centar mase granice objekta, prva komponenta definira
% kružnicu koja najbolje opisuje granicu objekta, a svaka sljedeća
% komponenta dodaje detalje na tu kružnicu.

% Definiraj kompleksnu funkciju.
z = B{1}(:,2) + 1i * B{1}(:,1);

% Izračunaj njenu Fourierovu transformaciju.
Z = fft(z);

% Odaberi samo značajne koeficijente Fourierove transformacije granice.
% Granicu značajnosti definiramo u odnosu na najsnažniju komponentu kao 1
% promil iznosa najsnažnije komponente.
Z_abs = abs(Z);
idx = find(Z_abs > max(Z_abs)*0.001);
[~, idx_s] = sort(Z_abs(idx), 'descend');
idx = idx(idx_s);

% Spremi sve bazne funkcije Fourierove transformacije u cmp{}.
z_num = length(z);
cmp = cell(z_num, 1);
for k = 1 : z_num
     CMP = zeros(z_num, 1);
     CMP(k) = 1;
     cmp{k} = ifft(CMP);
end

% Izračunaj i nacrtaj skucesivne aproksimacije granice gdje komponente
% dodajemo po snazi. Primjetite da je prva komponenta z_approx{1} centar
% mase granice predmeta, druga komponenta z_approx{1} je kružnica koja
% najbolje opisuje predmet, a sve ostale komponente dodaju daljnje detalje.
z_approx = cell(numel(idx), 1);
z_approx{1} = Z(idx(1))*cmp{idx(1)};
figure;
imshow(I);
axis image;
hold on;
plot(z_approx{1}, 'x');
for i = 2 : numel(idx)
    z_approx{i} = z_approx{i-1} + Z(idx(i))*cmp{idx(i)};
    plot(z_approx{i}, '.');
end


%% Računanje momenata
% Momente računamo izravno prema formuli. Tip podataka uint8 ne podržava
% računske operacije koje nam trebaju, a osim toga kod zbrajanja bi došlo
% do preljeva, pa zato sliku I prvo pretvaramo u binarnu sliku dvostruke
% preciznosti.

Id = double(I>0);

% Ne-centralni momenti.
M00 = sum(sum((x.^0) .* (y.^0) .* Id));
M10 = sum(sum((x.^1) .* (y.^0) .* Id));
M01 = sum(sum((x.^0) .* (y.^1) .* Id));

% Centar mase.
x_bar = M10 / M00;
y_bar = M01 / M00;

% Centralni momenti.
mu11 = sum(sum((x - x_bar).^1 .* (y - y_bar).^1 .* Id));
mu20 = sum(sum((x - x_bar).^2 .* (y - y_bar).^0 .* Id));
mu02 = sum(sum((x - x_bar).^0 .* (y - y_bar).^2 .* Id));

% Uzorkačke devijacije po x i y smjeru.
sigma_x = sqrt(mu20/(M00-1));
sigma_y = sqrt(mu02/(M00-1));

% Nacrtaj položaj centra mase te elipse koje odgovaraju područjima od
% jedne, dvije i tri standardne devijacije oko centra mase.
figure;
imagesc(I);
colormap(gray);
axis image;
hold on;
plot(x_bar, y_bar, 'rx', 'MarkerSize', 10, 'LineWidth', 3);
rectangle('Position', [x_bar-1*sigma_x y_bar-1*sigma_y 2*sigma_x 2*sigma_y], 'Curvature', [1 1], 'EdgeColor',  [1, 0.5, 0], 'LineWidth', 3);
rectangle('Position', [x_bar-2*sigma_x y_bar-2*sigma_y 4*sigma_x 4*sigma_y], 'Curvature', [1 1], 'EdgeColor',  [1, 0.5, 0], 'LineWidth', 2);
rectangle('Position', [x_bar-3*sigma_x y_bar-3*sigma_y 6*sigma_x 6*sigma_y], 'Curvature', [1 1], 'EdgeColor',  [1, 0.5, 0], 'LineWidth', 1);


%% Skelt(on)izacija ili MAT (engl. Medial Axis Transform)
% Binarni objekt možemo u potpunosti opisati pomoću njegove centralne osi
% ili skeleta te radijusa svih upisanih kružnica s centrima na sekeletu.
% Skelet se može analizirati kao graf, i ako je graf jednostavan bez
% ciklusa onda objekt ima jednostavnu topologiju (nema rupa), a ako graf
% ima cikluse onda objekt ima složenu topologiju (postoje rupe u objektu).

[L, n] = bwlabel(I, 8);

% Skeletizacija ili skeletonizacija.
S = bwmorph(1==L, 'skel', Inf);

figure;
IS = labeloverlay(I, S, Transparency=0);
imshow(IS);
axis image;

% Udaljenost od ruba.
dm = bwdist(0==L);

figure;
imagesc(dm);
axis image;
colorbar;

% Rekonstrukcija objekta kroz upisane kružnice. U diskretnom slučaju
% rekonstrukcija je aproksimativna (jer je i računanje skeleta
% aproksimativno).
Sdm = S .* dm;

figure;
imshow(I);
axis image;
idx = find(0 < Sdm);
[r,c] = ind2sub(size(Sdm),idx);
viscircles([c r], Sdm(idx));