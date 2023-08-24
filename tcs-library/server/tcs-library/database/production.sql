drop database if exists tcs_library;
create database tcs_library;
use tcs_library;

drop table if exists app_user_role;
drop table if exists app_role;
drop table if exists app_user;
drop table if exists comments;
drop table if exists review;
drop table if exists item_shelf;
drop table if exists item;

create table app_user (
    app_user_id int primary key auto_increment,
    username varchar(50) not null unique,
    password_hash varchar(2048) not null,
    enabled bit not null default(1)
);

create table app_role (
    app_role_id int primary key auto_increment,
    `name` varchar(50) not null unique
);

create table app_user_role (
    app_user_id int not null,
    app_role_id int not null,
    constraint pk_app_user_role
        primary key (app_user_id, app_role_id),
    constraint fk_app_user_role_user_id
        foreign key (app_user_id)
        references app_user(app_user_id),
    constraint fk_app_user_role_role_id
        foreign key (app_role_id)
        references app_role(app_role_id)
);

create table item (
	item_id int primary key auto_increment,
    identifier text,
    filename text
);

create table item_shelf (
	item_shelf_id int primary key auto_increment,
    page_number int default 1,
    item_id int,
    app_user_id int,
    constraint fk_item_shelf_item_id
		foreign key (item_id)
		references item(item_id),
	constraint fk_item_shelf_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id)
);

create table review (
	review_id int primary key auto_increment,
    review text,
    app_user_id int,
    item_id int,
    constraint fk_review_app_user_id
		foreign key (app_user_id)
        references app_user(app_user_id),
	constraint fk_review_item_id
		foreign key (item_id)
        references item(item_id)
);

insert into app_role (`name`) values
    ('USER'),
    ('ADMIN');

-- passwords are set to "P@ssw0rd!"
insert into app_user (username, password_hash, enabled)
    values
    ('john@smith.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('sally@jones.com', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
    ('alex123', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
	('emily22', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1),
	('daniel94', '$2a$10$ntB7CsRKQzuLoKY3rfoAQen5nNyiC/U60wBsWnnYrtQQi8Z3IZzQa', 1);

insert into app_user_role
    values
    (1, 2),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1);  
    
insert into item (identifier, filename) 
	values
    ("pride-and-prejudice_201907","pride-and-prejudice.pdf"),
    ('heidi0000unse_u3i9', 'heidi0000unse_u3i9.pdf'),
	('082-the-blackwing-puzzle-franklin-w.-dixon', '001-The Tower Treasure - Franklin W. Dixon.pdf'),
	('bram-stoker_dracula', 'bram-stoker_dracula.pdf'),
	('the-bachman-books-king-stephen-bachman-richard_202304', 'The Bachman Books (King, Stephen Bachman, Richard) (z-lib.org).pdf'),
	('thegiftofthemagilevel1', 'The Gift of the Magi (level 1).pdf'),
	('achristmascarol_201909', 'A-Christmas-Carol.pdf'),
	('the-great-gatsby-pdf-pg-64317', 'The Great Gatsby PDF pg64317.pdf');
    
insert into item_shelf (page_number, item_id, app_user_id)
	values
    (303, 1, 2),
    (200, 1, 1);

insert into review (review, item_id, app_user_id)
	values
    ("Book was pretty cool ngl", 1, 1),
    ("One of my favorites to read on a slow summer evening :)", 1, 5),
    ("This book offers a captivating exploration of the human experience through a diverse cast of characters. The narrative's pacing allows for deep connections as themes of love, struggle, and self-discovery unfold against vivid backdrops. While occasionally familiar, the storytelling remains engaging, though some character motivations could be more pronounced. Overall, this book resonates as a relatable and valuable read that delves into the intricacies of human connections and emotions.", 1, 3),
    ("Congratulations to this book for making clichés seem groundbreaking. The characters are as deep as a puddle after a drizzle, and the plot is more twisted than a pretzel factory. If you're into eye-rolling and sighing loudly, this masterpiece is for you.", 1, 2),
    
    ("So, this book dives into all these big life themes with a bunch of different characters, and even though you might see some stuff coming and wish some characters had more going on, it still hits you and makes you think about life and connections, you know?", 2, 1),
    ("Alright, check it – this book takes you on a ride through all sorts of deep life stuff with a bunch of characters. Sure, you might call a few twists, and a couple of characters could've used more oomph, but it's got that realness that makes you reflect on life and how we're all linked up.", 2, 4),
    ("Wow, just wow. This book completely swept me away on a rollercoaster of emotions. The characters are so vivid, their journeys are incredibly compelling, and the way everything ties together is sheer brilliance. I couldn't put it down, and I can't stop thinking about it. A must-read that'll stay with you.", 2, 2),
    
    ("Alright, so this book had its moments. The characters felt relatable, and some scenes hit the emotional bullseye. But let's not ignore the elephant in the room – a couple of plot twists were super predictable. And don't even get me started on that one character who did a total 180 out of the blue. Could've been better, but it's not a complete waste of time.", 3, 1),
    ("Honestly, I had higher hopes for this book. The characters were all over the place, and their motivations were like a puzzle missing half the pieces. The plot had potential, but the execution fell flat with clichés and déjà vu moments. There's room for improvement, maybe next time, right?", 3, 2),
    ("Ugh, I can't even >:( This book was a snooze-fest. The characters were cardboard cutouts with the depth of a puddle, and the plot? Ha, what plot? Predictable, cliché-ridden nonsense that I regret wasting time on. Don't bother, seriously.", 3, 3),
    ("This book has its moments, no doubt. The characters are interesting, and there's an attempt at digging deep into the human experience. However, some plot developments felt contrived, and character decisions often left me scratching my head. A mixed bag that could benefit from tighter storytelling.", 3, 4),
    ("Well, I finished it, but barely. The characters were forgettable, and the themes were presented with all the subtlety of a sledgehammer. The plot twists were eye-roll-inducing, and I couldn't help but roll my eyes at the clichés. Not my cup of tea, to be honest.", 3, 5),
    
    ("Diving into this book was like entering a universe of emotions. The characters are well-drawn, and the exploration of universal themes is commendable. While some parts felt predictable, the overall impact is undeniable. It's a journey worth taking, even with a few bumps along the way.", 4, 3),
    ("This book is an incredible journey through the human experience. The characters are deeply developed, and their stories intertwine in a masterful way. The themes explored are thought-provoking and left me pondering long after I turned the last page. A must-read that resonates on multiple levels.", 4, 1),
    ("This book fell short of my expectations. While it had potential, the characters lacked depth, and the plot often meandered. Predictable twists and underdeveloped relationships left me disappointed. There are better options out there.", 4, 2),
    
    ("This book offers a profound exploration of human nature and connections. The characters feel genuine, though some could have been more fleshed out. The plot, while occasionally predictable, manages to evoke genuine emotions. Worth considering for readers interested in introspective narratives.", 5, 3),
    ("This book has its strengths and weaknesses. The characters are relatable, yet their decisions sometimes lack depth. The plot veers between engaging and cliché, with moments of genuine brilliance and occasional monotony. An average read that offers both enjoyable and lackluster aspects.", 5, 4),
    ("This book is an incredible journey through the human experience. The characters are deeply developed, and their stories intertwine in a masterful way. The themes explored are thought-provoking and left me pondering long after I turned the last page. A must-read that resonates on multiple levels.", 5, 5),
    
    ("Calling all fellow nerds – this book is a treasure trove of awesomeness! The characters are like our fantasy team-up, each with their quirks and strengths. The plot? A rollercoaster of twists that had me channeling my inner detective. From epic showdowns to emotional breakthroughs, this book nails the balance between action and depth. As a geek, this was like a literary convention, and I'm here for it.", 6, 2),
    ("This book is thoughtfully crafted, with characters whose growth feels genuine. The plot has its twists and turns, some anticipated, others not. The narrative's attention to emotional depth makes it a rewarding read overall.", 6, 5),

    ("This book held promise, but the ending left me unsatisfied. The characters' development was engaging, yet the plot resolution felt rushed. It's a decent read that could have been more fulfilling with a stronger conclusion.", 7, 1),
    ("This book single-handedly rekindled my love for reading. The characters are my new companions, and their journeys felt like my own. The plot kept me engaged with its twists and turns, reminding me why books are a window to endless possibilities. A masterpiece that's reignited my imagination.", 7, 3),

    ("Honestly, I struggled to connect with this book. The characters felt distant, and their stories failed to evoke empathy. While some plot elements intrigued, they were overshadowed by predictable turns. It's a miss for me, unfortunately.", 8, 2),
    ("This book offers a solid reading experience. The characters are relatable, and their journeys kept me engaged. While some plot elements were predictable, there were enough unexpected turns to maintain my interest. A commendable effort that I'd recommend to fellow readers.", 8, 4);

select * from item_shelf;
SELECT * FROM item;
SELECT * FROM review;
select * from app_user;

select au.username, r.review, au.app_user_id, r.item_id
from app_user au inner join review r on r.app_user_id = au.app_user_id;
