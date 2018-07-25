function getAllBooks() {
  var bookContainer = document.getElementById("BookContainer");
  $.ajax(
    {
      type: "GET",
      url: "/rest/books",
      success: function (books) {
        $("#BookContainer").empty();
        books.forEach(function (book) {
          date = new Date(book.publishDate);
          var day = date.getDate();
          var month = date.getMonth();
          var year = date.getFullYear();
          var stringDate = day + ' -' + month + '- ' + year;
          var info = book.name + " " + stringDate + " " + book.publisher.name
          var li = document.createElement("li");
          var link = document.createElement("a");
          li.appendChild(document.createTextNode(info));
          link.setAttribute('href', "test/" + book.id);
          link.classList.add("btn");
          link.classList.add("btn-outline-success");
          var deleteLink =  document.createElement("a");
          deleteLink.classList.add("btn");
          deleteLink.classList.add("btn-outline-success");
          deleteLink.appendChild(document.createTextNode("delete"));
          deleteLink.addEventListener('click', function () {
            deleteBook(book.id);
          });
          link.appendChild(document.createTextNode("edit"));
          li.appendChild(link);
          li.appendChild(deleteLink);
          bookContainer.appendChild(li);
        });
      },
      error: function (error) {
        console.log("Error:");
        console.log(error);
        console.log(error.responseText);
      }
    });
};
function deleteBook(id) {
  $.ajax(
    {
      type: "DELETE",
      url: "/rest/books/" + id,
      success: function () {
        console.log("success");
        getAllBooks();
      }
    });
}
function removeBook() {
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