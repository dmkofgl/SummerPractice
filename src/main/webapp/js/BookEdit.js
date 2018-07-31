'use strict'
var book;
var authors;
var publishers;
function my_f(Id) {
	var o = document.getElementById(Id)
	o.style.display = (o.style.display == 'none') ? 'inline-block' : 'none'
}
function my_hideAndView(hideId, viewId) {
	var hide = document.getElementById(hideId)
	var view = document.getElementById(viewId)
	hide.style.display = (hide.style.display == 'none') ? 'inline-block' : 'none'
	view.style.display = (view.style.display == 'none') ? 'inline-block' : 'none'
}
function getBook() {
	var id = document.location.pathname.split('/')[2];
	$.ajax(
		{
			type: "GET",
			url: "/rest/books/" + id,
			success: setBook,
			error:  function (request, status, error) {
				$("#editBook").remove();
				console.log("Error:");
				console.log(error);
				console.log(status);
				console.log(request);
				$("body").append(document.createTextNode(error))
			}
		});
};
function setBook(receiveBook) {
	window.book = receiveBook;
	getPublishers();
	$("[name=publishDate]").val(dateToString())
	$("[name=name]").val(book.name);
	viewBookAuthos(book.authors);
	getAuthors();

}
function dateToString() {
	var date = new Date(book.publishDate);
	var day = ("0" + date.getDate()).slice(-2);
	var month = ("0" + (date.getMonth() + 1)).slice(-2);
	var year = date.getFullYear();
	var stringDate = (year) + "-" + (month) + "-" + (day);
	return stringDate;
}
function setPublish() {
	var publisherList = document.getElementById("publishers");
	publisherList.value = book.publisher.id;
}
function viewBookAuthos(authors) {
	authors.forEach(author => addBookAuhtor(author));
}
function removeBookAuthor(listItem, button) {
	var authorsList = document.getElementById("otherAuthors");
	var option = document.createElement("option");
	option.setAttribute('value', button.value);
	option.appendChild(document.createTextNode(button.innerText));
	authorsList.appendChild(option);
	listItem.remove();
	var i;
	for (i = 0; i < book.authors.length; i++) {
		if (book.authors[i].id == button.value) {
			book.authors.splice(i, 1);
			break;
		}
	}

}
function addBookAuhtor(author) {
	var authorsList = document.getElementById("authors");
	var li = document.createElement("li");
	var item = document.createElement("button");
	item.setAttribute('value', author.id);
	item.appendChild(document.createTextNode(author.firstName + " " + author.lastName));
	li.appendChild(item);
	item.classList.add("btn");
	var removeButton = document.createElement("button");
	removeButton.classList.add("btn");
	removeButton.classList.add("btn-outline-success");
	removeButton.setAttribute('value', author.id);
	removeButton.addEventListener('click', function () {
		removeBookAuthor(li, item);
	});
	removeButton.appendChild(document.createTextNode("remove"));
	li.appendChild(removeButton);
	authorsList.appendChild(li);
}
function getPublishers() {
	$.ajax(
		{
			type: "GET",
			url: "/rest/publishers",
			success: function (allPublishers) {
				window.publishers = allPublishers;
				var dropList = document.getElementById("publishers");
				publishers.forEach(function (publisher) {
					var option = document.createElement("option");
					option.setAttribute('value', publisher.id);
					option.appendChild(document.createTextNode(publisher.name));
					dropList.appendChild(option);
				});
				setPublish();
			},
			error: function (error) {
				console.log("Error:");
				console.log(error);
				console.log(error.responseText);
			}
		});
};
function getAuthors() {
	$.ajax(
		{
			type: "GET",
			url: "/rest/authors",
			success: function (allAuthors) {
				window.authors = allAuthors.filter(author => !book.authors.some(function (bookAuthor) {
					return bookAuthor.id == author.id;
				}));
				var authorsList = document.getElementById("otherAuthors");
				authors.forEach(function (author) {
					var option = document.createElement("option");
					option.setAttribute('value', author.id);
					option.appendChild(document.createTextNode(author.firstName + " " + author.lastName));
					authorsList.appendChild(option);
				});
			},
			error: function (error) {
				console.log("Error:");
				console.log(error);
				console.log(error.responseText);
			}
		});
};
function addAuthor() {
	var authorsList = document.getElementById("otherAuthors");
	var author = authors.filter(athr => athr.id == authorsList.value)[0];
	addBookAuhtor(author);
	authorsList.remove(authorsList.selectedIndex);
	book.authors.push(author);
}
function confirm() {
	var nb = {}
	book.name = $("[name=name]").val();
	book.publishDate = $("[name=publishDate]").val();
	var publisherId = document.getElementById("publishers").value;
	var publisher = publishers.filter(publsh => publsh.id == publisherId)[0];
	book.publisher = publisher;
	console.log(book);
	$.ajax({
		url: "/rest/books/" + book.id,
		type: 'PUT',
		dataType: 'json',
		contentType: 'application/json',
		success: function (data) {
			alert("send");
		},
		data: JSON.stringify(book)
	});
}
