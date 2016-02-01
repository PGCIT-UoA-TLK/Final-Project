CREATE TABLE article (
	article_id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	title VARCHAR(250) NOT NULL DEFAULT '',
	body LONG VARCHAR NOT NULL DEFAULT ''
	);
INSERT INTO article (title,body) VALUES ('Duis bibendum, felis sed','Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit.');
INSERT INTO article (title,body) VALUES ('Maecenas leo odio','Praesent blandit. Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.');
INSERT INTO article (title,body) VALUES ('Morbi porttitor lorem','Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros. Suspendisse accumsan tortor quis turpis.');
INSERT INTO article (title,body) VALUES ('Maecenas tristique','Curabitur at ipsum ac tellus semper interdum. Mauris ullamcorper purus sit amet nulla. Quisque arcu libero, rutrum ac, lobortis vel, dapibus at, diam.');
INSERT INTO article (title,body) VALUES ('Suspendisse potenti','Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Vivamus vestibulum sagittis sapien. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.');
INSERT INTO article (title,body) VALUES ('Duis bibendum','Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat. Vestibulum sed magna at nunc commodo placerat.');
INSERT INTO article (title,body) VALUES ('Integer ac leo','Cras mi pede, malesuada in, imperdiet et, commodo vulputate, justo. In blandit ultrices enim. Lorem ipsum dolor sit amet, consectetuer adipiscing elit.');
INSERT INTO article (title,body) VALUES ('Quisque id justo sit amet','Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec pharetra, magna vestibulum aliquet ultrices, erat tortor sollicitudin mi, sit amet lobortis sapien sapien non mi. Integer ac neque.');
INSERT INTO article (title,body) VALUES ('Phasellus in felis','In sagittis dui vel nisl. Duis ac nibh. Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.');
INSERT INTO article (title,body) VALUES ('Nullam sit amet turpis','Aenean fermentum. Donec ut mauris eget massa tempor convallis. Nulla neque libero, convallis eget, eleifend luctus, ultricies eu, nibh.');
INSERT INTO article (title,body) VALUES ('Aenean fermentum','Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum. Integer a nibh.');
INSERT INTO article (title,body) VALUES ('In sagittis dui vel nisl','Phasellus in felis. Donec semper sapien a libero. Nam dui.');
INSERT INTO article (title,body) VALUES ('Donec diam neque, vestibulum','Quisque id justo sit amet sapien dignissim vestibulum. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Nulla dapibus dolor vel est. Donec odio justo, sollicitudin ut, suscipit a, feugiat et, eros.');
INSERT INTO article (title,body) VALUES ('Cras mi pede, malesuada in ','Integer ac leo. Pellentesque ultrices mattis odio. Donec vitae nisi.');
INSERT INTO article (title,body) VALUES ('Integer tincidunt ante vel ','Duis bibendum. Morbi non quam nec dui luctus rutrum. Nulla tellus.');
INSERT INTO article (title,body) VALUES ('Cum sociis natoque penatibus','Suspendisse potenti. In eleifend quam a odio. In hac habitasse platea dictumst.');
INSERT INTO article (title,body) VALUES ('Curabitur at ipsum ac','Maecenas tristique, est et tempus semper, est quam pharetra magna, ac consequat metus sapien ut nunc. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Mauris viverra diam vitae quam. Suspendisse potenti.');
INSERT INTO article (title,body) VALUES ('Aliquam quis turpis eget','Morbi porttitor lorem id ligula. Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.');
INSERT INTO article (title,body) VALUES ('Praesent blandit. Nam nulla','Maecenas leo odio, condimentum id, luctus nec, molestie sed, justo. Pellentesque viverra pede ac diam. Cras pellentesque volutpat dui.');
INSERT INTO article (title,body) VALUES ('Duis aliquam convallis nunc','Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum.');

