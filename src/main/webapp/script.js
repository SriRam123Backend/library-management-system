var global = 1;
onLoadStartUp();
var gender = "";
var TotalBooks=0;
var TotalUser = 0;
var TotalLoggedInUser = 0;
var TotalLoggeUser = 0;
var Newusers=[];
var Newbooks=[];
var returnBookISBN=0;
var e = 0;
var z;
var d;
var u;
var searchingBooksDetails=[];
var specificBookDetails=[];
var BorrowBookDetails=[];
var AddedBookDetails =[];
var LMSUsers=[]
var Phone_NumberOfUser = "";
var ParticularPersonBorrowBooksDetails =[]
var AmountOfFine =[];
var bookimageArray = ["q.png","w.png","e.png","r.png","t.png","y.png","i.png"];
let websocket;
////////////////////////////////////////////////////////////////////////////////////////////////////
function login() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && this.status == 200) {
			var responseObject = JSON.parse(this.responseText);
			if (responseObject.Message != "User is not Found" && responseObject.Role == "ADMIN") {
				gender = responseObject.Gender;
				AdminInterfaces();
				websocketFunction();
			} else if (responseObject.Message != "User is not Found" && responseObject.Role == "USER") {
				gender = responseObject.Gender;
				UserInterfaces();
                websocketFunction();
			} else {
				  alert("Plese Enter Correct Details")
				}
			}
		}
	var reqObj = {};
    reqObj.Phone_Number = document.getElementById("signInPhoneNumber").value;
	reqObj.Password = document.getElementById("signInPassword").value;
	xhr.open("POST", "./Login");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("Phone_Number=" + reqObj.Phone_Number + "&Password=" + reqObj.Password);
}

function websocketFunction()
{
	websocket = new WebSocket("ws:"+window.location.hostname+":8080/Library_Management_System_Software/Notification");
	websocket.onmessage = (message) =>{
		console.log(message,message.data);
	}
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function SignUpProcess() {
	$('.summa').hide();
	$('#SignUpContainer').show();
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function SignInProcess() {
	$('.summa').hide();
	$('.summa1').hide();
	$('.summa2').hide();
	$('#Container1').show();
}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function showingPassword(PasswordID,_divName,_iconName) {
	var x = document.getElementById(PasswordID);
	if (x.type === "password") {
		document.getElementById(_iconName).remove();
		var icon = document.createElement("i");
		document.getElementById(_divName).appendChild(icon);
		icon.setAttribute("id", _iconName);
		icon.setAttribute("class", "fa-regular fa-eye");
		icon.addEventListener("click",()=>{
			showingPassword(PasswordID,_divName,_iconName)
		});
		x.type = "text";
	} else {
		document.getElementById(_iconName).remove();
		var icon = document.createElement("i");
		document.getElementById(_divName).appendChild(icon);
		icon.setAttribute("id", _iconName);
		icon.setAttribute("class", "fa-regular fa-eye-slash");
		icon.addEventListener("click",()=>{
			showingPassword(PasswordID,_divName,_iconName)
		});
		x.type = "password";
	}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function SignUp() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Message=="Successfull" && responseObject.Role=="USER")
			{
				gender = responseObject.Gender;
				UserInterfaces();
			}
			else if(responseObject.Message == "Something went wrong" || responseObject.Message =="User is not Found")
			{
				console.log("Something went wrong");
			}
		}
	}
	var User_Details = {};
	User_Details.First_Name = document.getElementById("FirstName").value;
	User_Details.Last_Name = document.getElementById("LastName").value;
	User_Details.Date_Of_Birth = document.getElementById("DateOfBirth").value;
	User_Details.Phone_Number = document.getElementById("PhoneNumber").value;
	User_Details.Password = document.getElementById("signUpPassword").value;
	User_Details.Gender = document.getElementById("Gender").value;
	xhr.open("POST", "./SignUp_Page");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(User_Details));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function AdminInterfaces() {
	
	document.getElementById("UserContainer").innerHTML="";
	$('#UserContainer').hide();
	$('#fullContainer').show()
	document.getElementById("contentsBox").style.height="409px";
	if(gender=="MALE")
	{
			var imgDiv = document.createElement("img");
			document.getElementById("profileImage").appendChild(imgDiv);
			imgDiv.setAttribute("id", "imgSize");
			imgDiv.setAttribute("src","m.png");
	}
    else if(gender=="FEMALE")
	{
			var imgDiv = document.createElement("img");
			document.getElementById("profileImage").appendChild(imgDiv);
			imgDiv.setAttribute("id", "imgSize");
			imgDiv.setAttribute("src","f.png");
	}
    updateBooksAndUser();
	document.getElementById("dashboard").addEventListener("click", () => {
		global = 2;
		changeColor("dashboard")
		updateBooksAndUser();
	})
	document.getElementById("allBook").addEventListener("click", () => {
		global = 2;
		changeColor("allBook");
		allBookss();
	})
	document.getElementById("addBook").addEventListener("click", () => {
		global = 2;
		changeColor("addBook");
		addBooks();
	})
	document.getElementById("updateBook").addEventListener("click", () => {
		global = 2;
		changeColor("updateBook");
		updatebookDetails();
	})
	document.getElementById("members").addEventListener("click", () => {
		global = 2;
		changeColor("members");
		members();
	})
	document.getElementById("issueBooks").addEventListener("click", () => {
		global = 2;
		changeColor("issueBooks");
		issueBooks();
	})
	document.getElementById("terms").addEventListener("click", () => {
		global = 2;
		changeColor("terms");
		termsAndConditions();
	})
	document.getElementById("settings").addEventListener("click", () => {
		global = 2;
		changeColor("settings");
		settings();
	})
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function changeColor(a) {
	var b = document.getElementsByClassName("categories")
	for (let i = 0; i < b.length; i++) {
		b[i].style.color = "grey";
		b[i].style.borderRight = "none";
		b[i].addEventListener("mouseover",()=>{b[i].style.color="#0ef"})
		b[i].addEventListener("mouseout",()=>{b[i].style.color="gray"})
	}
	document.getElementById(a).style.color = "#0ef";
	document.getElementById(a).style.borderRight = "2px solid #0ef";
	
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function dashboard() {
	$('.summa').hide();
	$('#main').show();
	document.getElementById("q").innerText=TotalLoggedInUser;
	document.getElementById("t").innerText=TotalLoggedOutUser;
	document.getElementById("r").innerText=TotalBooks;
	document.getElementById("s").innerText=TotalUser;
	NewUserDetails();
	NewBooksDetails();
	list();
	document.getElementById("dashboard").style.color = "#0ef";
	document.getElementById("dashboard").style.borderRight = "2px solid #0ef";
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function allBookss() {
	$('.summa').hide();
	$('#allBookswholeDiv').show();
	searchingBooks();
    document.getElementById("search").addEventListener("input",searchingBooks);
    document.getElementById("types").addEventListener("input",searchingBooks);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function addBooks() {
	$('.summa').hide()
	$('#forBookDetailsAdded').hide();
	$('#addBookswholeDiv').show();
	$('#container').show();
	$('#size9').show();
	let todayDate = new Date();
    todayDate.setDate(todayDate.getDate())
    let current = todayDate.toISOString().substring(0,10);
    document.getElementById("PublishedDate").max= current;
        		   
	document.getElementById("IsbnNo").addEventListener("click",()=>
	{ 
		$('#forBookDetailsAdded').hide();
		$('#size9').show();
	})
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function issueBooks() {
	$('.summa').hide()
	$('#issueBookswholeDiv').show();
	IssuedBooks();
	document.getElementById("borrowBookName").addEventListener("input",IssuedBooks);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function members() {
	$('.summa').hide()
	$('#memberswholeDiv').show();
	LMSUserDetails();
	document.getElementById("UserName").addEventListener("input",LMSUserDetails);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function termsAndConditions() {
	$('.summa').hide()
	$('#termsAndConditions').show()
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function settings() {
	$('.summa').hide();
	$('#ProfileWholeDIv').hide();
	$('#UpdateProfileWholeDIv').hide();
	$('#settings1').show()
	$('#SettingFirstPage').show();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function NewUserDetails() {
	
	var UserDiv;
	var a = 1;
	
	 if(global == 1)
	 {
        var wholeUserDiv = document.createElement("div");
       	document.getElementById("NewUser").appendChild(wholeUserDiv);
		wholeUserDiv.setAttribute("id", "wholeUserDiv");
	 }
	 document.getElementById("wholeUserDiv").innerHTML="";
	for (let i = 0; i<Newusers.length; i++) {
		
/*			console.log(Newusers[i].Name);
			console.log(Newusers[i].Phone_Number);
			console.log(Newusers[i].TotalBooksIssued);
			console.log(Newusers[i].Gender);*/
			
			UserDiv = document.createElement("div");
			UserDiv.setAttribute("class", "Userdetails");
			a += 1;
			UserDiv.setAttribute("id", a);
			document.getElementById("wholeUserDiv").appendChild(UserDiv);
			
			if(Newusers[i].Gender=="MALE")
			{
			var imgDiv = document.createElement("img");
			document.getElementById(a).appendChild(imgDiv);
			imgDiv.setAttribute("class", "imageSize");
			imgDiv.setAttribute("src", "m.png");
			}
            else if(Newusers[i].Gender=="FEMALE")
	        {
			var imgDiv = document.createElement("img");
			document.getElementById(a).appendChild(imgDiv);
			imgDiv.setAttribute("class", "imageSize");
			imgDiv.setAttribute("src", "f.png");
	        }
	
			var userName = document.createElement("span");
			document.getElementById(a).appendChild(userName);
			var m = "userName"+a;
			userName.setAttribute("id", m);
			document.getElementById(m).innerText=Newusers[i].Name;
			
			var mobNum = document.createElement("span");
			document.getElementById(a).appendChild(mobNum);
			var n = "mobNum"+a;
			mobNum.setAttribute("id",n);
			mobNum.setAttribute("class","text11")
			document.getElementById(n).innerText=Newusers[i].Phone_Number;
		
			var TotalBooksIssued = document.createElement("span");
			document.getElementById(a).appendChild(TotalBooksIssued);
			var o = "TotalBooksIssued"+a;
			TotalBooksIssued.setAttribute("id",o);
			document.getElementById(o).innerText=Newusers[i].TotalBooksIssued;
			
		  }		  
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function NewBooksDetails() {
	var BookDiv;
	var c = 90;
		
	 if(global == 1)
	 {
			var wholeBookDiv = document.createElement("div");
			document.getElementById("NewBooks").appendChild(wholeBookDiv);
			wholeBookDiv.setAttribute("id", "wholeBookDiv");
	 }
		document.getElementById("wholeBookDiv").innerHTML="";
	for (let k = 0; k < Newbooks.length; k++) {
			
/*			console.log(Newbooks[k].BookName);
			console.log(Newbooks[k].AuthorName);
			console.log(Newbooks[k].IsbnNo);
			console.log(Newbooks[k].IssuedDate);*/
			
			BookDiv = document.createElement("div");
			document.getElementById("wholeBookDiv").appendChild(BookDiv);
			BookDiv.setAttribute("class", "Bookdetails ");
			c += 1;
			BookDiv.setAttribute("id", c);
			
			var imgDiv = document.createElement("img");
			document.getElementById(c).appendChild(imgDiv);
			imgDiv.setAttribute("class", "imageSize");
			imgDiv.setAttribute("src", "b.png");
			
			var BookName = document.createElement("span");
			document.getElementById(c).appendChild(BookName);
			var u = "BookName" + c
			BookName.setAttribute("id",u);
			document.getElementById(u).innerText= Newbooks[k].Book_Name;
			
			var AuthorName = document.createElement("span");
			document.getElementById(c).appendChild(AuthorName);
			var v = "AuthorName"+c;
			AuthorName.setAttribute("id", v);
			document.getElementById(v).innerText=Newbooks[k].Author_Name;
			
			var IsbnNo = document.createElement("span");
			document.getElementById(c).appendChild(IsbnNo);
			var w = "IsbnNo"+c;
			IsbnNo.setAttribute("id", w);
			document.getElementById(w).innerText=Newbooks[k].ISBN_No;
			
			var IssuedDate = document.createElement("span");
			document.getElementById(c).appendChild(IssuedDate);
			var x = "IssuedDate"+c;
			IssuedDate.setAttribute("id", x);
			document.getElementById(x).innerText=Newbooks[k].Published_Date;
		}
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function updatebookDetails() {
	$('.summa').hide();
	$('#forBookDetailsUpdated').hide();
	$('#updateBookDetail').show();
	$('#container2').show();
	document.getElementById("inputIsbnNo").value="";
	document.getElementById('UpdatedBookDetail').innerHTML="";
	console.log(document.getElementById("inputsDiv").childNodes);
	let todayDate = new Date();
    todayDate.setDate(todayDate.getDate())
    let current = todayDate.toISOString().substring(0,10);
    document.getElementById("PublishedDate1").max= current;
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function list() {
/*	if (global == 1) {
		document.getElementById("NewUser").innerHTML += "<div class='listall'>List All</div>";
		document.getElementById("NewBooks").innerHTML += "<div class='listall'>List All</div>";
	}*/
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function viewsettings() {
	
	$('#SettingFirstPage').hide();
	$('#ProfileWholeDIv').show();
	var xhr = new XMLHttpRequest()
	xhr.onreadystatechange = function() {
	  if(xhr.readyState == 4 && xhr.status == 200)
	  {
		    var responseObject = JSON.parse(this.responseText);
		    
		    var ProfileWholeDIv = document.createElement("div");
    		document.getElementById("settings1").appendChild(ProfileWholeDIv);
			ProfileWholeDIv.setAttribute("id", "ProfileWholeDIv");
			
			document.getElementById("ProfileWholeDIv").innerHTML=""; 
			 
			var heading16 = document.createElement("span");
    		document.getElementById("ProfileWholeDIv").appendChild(heading16);
			heading16.setAttribute("id", "heading16");
			document.getElementById("heading16").innerText="Profile Details"
	
			var viewProfileWholeDIv = document.createElement("div");
    		document.getElementById("ProfileWholeDIv").appendChild(viewProfileWholeDIv);
			viewProfileWholeDIv.setAttribute("id", "viewProfileWholeDIv");
	        
	        
	       	var BackButtonOfSettings = document.createElement("span");
    		document.getElementById("ProfileWholeDIv").appendChild(BackButtonOfSettings);
			BackButtonOfSettings.setAttribute("id", "BackButtonOfSettings");
			document.getElementById("BackButtonOfSettings").innerHTML="<i class='fa-solid fa-chevron-left backButtonSize' onclick='settings()'></i>"
	        
			var pbg = document.createElement("img");
    		document.getElementById("viewProfileWholeDIv").appendChild(pbg);
    		if(gender == "FEMALE")
	        {
			   pbg.setAttribute("src", "vbg.jpg");
			}
			else if(gender == "MALE")
	        {
			   pbg.setAttribute("src", "bbg.jpg");
			}
			pbg.setAttribute("id", "pbg");
	
	        if(responseObject.Gender == "MALE")
	        {
			var profileImageSize = document.createElement("img");
    		document.getElementById("viewProfileWholeDIv").appendChild(profileImageSize);
    		profileImageSize.setAttribute("src", "m.png");
			profileImageSize.setAttribute("id", "profileImageSize");
			}
	      
	      	else if(responseObject.Gender == "FEMALE")
	        {
			var profileImageSize = document.createElement("img");
    		document.getElementById("viewProfileWholeDIv").appendChild(profileImageSize);
    		profileImageSize.setAttribute("src", "f.png");
			profileImageSize.setAttribute("id", "profileImageSize");
			}
	      
			var heading17 = document.createElement("span");
    		document.getElementById("viewProfileWholeDIv").appendChild(heading17);
			heading17.setAttribute("id", "heading17");
			document.getElementById("heading17").innerText=responseObject.Name;
	
			var Phno = document.createElement("span");
    		document.getElementById("viewProfileWholeDIv").appendChild(Phno);
    		Phno.setAttribute("id", "Phno");
			Phno.setAttribute("class", "profileiconSize");
			document.getElementById("Phno").innerHTML ="<i class='fa-solid fa-phone'></i><span id='UserPhno' class='alignmentText'></span>";
			document.getElementById("UserPhno").innerText=responseObject.Phone_Number;
	
			var dob = document.createElement("span");
    		document.getElementById("viewProfileWholeDIv").appendChild(dob);
			dob.setAttribute("id", "dob");
			dob.setAttribute("class", "profileiconSize");
			document.getElementById("dob").innerHTML ="<i class='fa-solid fa-calendar-days'></i><span id='Userdob' class='alignmentText'></span>";
			document.getElementById("Userdob").innerText=responseObject.Date_Of_Birth;
	
			var gen = document.createElement("span");
    		document.getElementById("viewProfileWholeDIv").appendChild(gen);
			gen.setAttribute("id", "gen");
			gen.setAttribute("class", "profileiconSize");
			document.getElementById("gen").innerHTML ="<i class='fa-solid fa-user'></i><span id='Usergen' class='alignmentText'></span>";
			document.getElementById("Usergen").innerText=responseObject.Gender;
	   }
	}
	
  	xhr.open("POST","./filter/CurrentUser");
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send();
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function updatesettings() {
	$('#SettingFirstPage').hide();
	$('#ProfileWholeDIv').hide();
	$('#UpdateProfileWholeDIv').show();
	
	var xhr = new XMLHttpRequest()
	xhr.onreadystatechange = function() {
	  if(xhr.readyState == 4 && xhr.status == 200)
	  {
		    var responseObject = JSON.parse(this.responseText);
		    
		    var UpdateProfileWholeDIv = document.createElement("div");
    		document.getElementById("settings1").appendChild(UpdateProfileWholeDIv);
			UpdateProfileWholeDIv.setAttribute("id", "UpdateProfileWholeDIv");
			
			document.getElementById("UpdateProfileWholeDIv").innerHTML=""; 
			 
			var heading21 = document.createElement("span");
    		document.getElementById("UpdateProfileWholeDIv").appendChild(heading21);
			heading21.setAttribute("id", "heading21");
			document.getElementById("heading21").innerText="Update Profile Details"
	        
	       	var BackButtonOfSettings1 = document.createElement("span");
    		document.getElementById("UpdateProfileWholeDIv").appendChild(BackButtonOfSettings1);
			BackButtonOfSettings1.setAttribute("id", "BackButtonOfSettings1");
			document.getElementById("BackButtonOfSettings1").innerHTML="<i class='fa-solid fa-chevron-left backButtonSize' onclick='settings()'></i>"
			
			var UpdateDetailsContainer = document.createElement("div");
    		document.getElementById("UpdateProfileWholeDIv").appendChild(UpdateDetailsContainer);
			UpdateDetailsContainer.setAttribute("id", "UpdateDetailsContainer");
	  		UpdateDetailsContainer.innerHTML=`<div id='inputsDiv1'><div class='input-box1'><input class="input1" type='text' required id='FirstName1' autocomplete="off"><label class="label1" for=''>First Name</label></div><div class='input-box1'><input class="input1" type='text' required id='LastName1' autocomplete="off"><label class="label1" for=''>Last Name</label></div><div class='input-box1'><input class="input1" type='date' required id="DateOfBirth1" max='2013-01-01' autocomplete="off"/><label class="label1" for=''>Date of Birth</label></div><div class='input-box1'><input class="input1" type='number' required id='PhoneNumber1' autocomplete="off"><label class="label1" for=''>Phone Number</label></div><div class='input-box1' id='pass2'><input class="input1" type='password' required id='password2' autocomplete="off"><i class='fa-regular fa-eye-slash' id='showPassword2' onclick='showingPassword("password2","pass2","showPassword2")'></i><label class="label1" for=''>Password</label></div><button id="button1" type='submit' onclick="UpdateUserDetails()">Update</button>`
	   
	        var o = responseObject.Date_Of_Birth.split("/");
		    var formattedDate = o[2]+"-"+o[1]+"-"+o[0];
		    console.log(o[2]+"-"+o[1]+"-"+o[0]);
	   	    document.getElementById("FirstName1").value = responseObject.First_Name;
			document.getElementById("LastName1").value = responseObject.Last_Name;
			document.getElementById("DateOfBirth1").value =formattedDate;
			document.getElementById("PhoneNumber1").value = responseObject.Phone_Number;
			document.getElementById("password2").value =responseObject.Password;
			
			
		    document.getElementById("UpdateDetailsContainer").style.display = "none";
			forConform(responseObject.Role);
	   }
	}
	
 	xhr.open("POST","./filter/CurrentUser");
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send();
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function logOut()
{
	var logOut = document.createElement("div");
	document.getElementById("profileDiv").appendChild(logOut);
	logOut.setAttribute("id", "logOut");
	logOut.setAttribute("class", "summa");
    logOut.innerHTML="<span>Log Out</span>";
	document.getElementById("signInPhoneNumber").value="";
    document.getElementById("signInPassword").value="";
    document.getElementById("logOut").addEventListener("click",logOutFunction)
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function addBooksSubmit()
{
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function()
	{
		 if (xhr.readyState == 4 && this.status == 200) {
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Message == "Successfully Book Added")
			{				   
				console.log(responseObject.IsbnNo);
				console.log(responseObject.Pages);
			    AddedBookDetails =[responseObject.Book_Name,responseObject.Author_Name,responseObject.ISBN_No,responseObject.Pages,responseObject.Published_Date,responseObject.Category,responseObject.Available_Count,responseObject.Category,responseObject.Floor_No,responseObject.Shelve_Number];
				$('#size9').hide();
				$('#forBookDetailsAdded').show();
				websocket.send(responseObject.ISBN_No);
				alldetailsOfAddBooksAndUpdatedBooks("UpdatedBookDetails");
				
				document.getElementById("IsbnNo").value="";
		        document.getElementById("BookName").value="";
		        document.getElementById("AuthorName").value="";
	         	document.getElementById("pages").value="";
		        document.getElementById("PublishedDate").value="";
	        	document.getElementById("category").value="None";
	        	document.getElementById("Available_Count").value="";
		        document.getElementById("Floor_No").value="";
		        document.getElementById("Department").value="";
	         	document.getElementById("Shelve_Number").value="";
	        	
			}
			else if(responseObject.Message="Invalid Input Please Check it")
			{
				alert("Invalid Inputs Please Check it");
			}
		}
	}
	var BookDetails = {};
	BookDetails.isbnNo=document.getElementById("IsbnNo").value;
	BookDetails.bookName = document.getElementById("BookName").value;
	BookDetails.authorName = document.getElementById("AuthorName").value;
	BookDetails.pages = document.getElementById("pages").value;
	BookDetails.publishedDate = document.getElementById("PublishedDate").value;
    BookDetails.category = document.getElementById("category").value;
    BookDetails.availableStock = document.getElementById("Available_Count").value;
    BookDetails.floorNo = document.getElementById("Floor_No").value;
    BookDetails.department = document.getElementById("Department").value;
    BookDetails.shelveNo = document.getElementById("Shelve_Number").value;
    xhr.open("POST", "./AddBooks");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(BookDetails));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function searchingBooks()
{
	searchingBooksDetails=[];
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function()
	{
		if (xhr.readyState == 4 && this.status == 200) {
			var responseObject = JSON.parse(this.responseText);
			for(let i=0;i<responseObject.length;i++)
			{
				searchingBooksDetails.push(responseObject[i]);
			}
		}
		console.log(searchingBooksDetails);
		showingBookDetails();
	}
	var SearchBooks = {};
	SearchBooks.bookName = document.getElementById("search").value;
	SearchBooks.category = document.getElementById("types").value;
	SearchBooks.NoOfTheBook = "";
	xhr.open("POST", "./filter/SearchBooks");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(SearchBooks));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function updateBooksAndUser()
{
    Newusers=[];
    Newbooks=[];
    console.log("update");
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && this.status == 200) {
			var responseObject = JSON.parse(this.responseText)
			  TotalBooks = responseObject[0].TotalBooks;
			  TotalUser = responseObject[0].TotalUser;
			  TotalLoggedInUser= responseObject[0].TotalLoggedInUser;
			  TotalLoggedOutUser = responseObject[0].TotalLoggedOutUser;
			for(let i=1;i<responseObject.length;i++)
			{
				if(i<=6)
				{ 
					Newusers.push(responseObject[i]);
				}
				else if(i>6 && i<=12)
				{
					Newbooks.push(responseObject[i]);
				}
				else{
					break;
				}	
			 }	
			 dashboard();
		   }	
	}
    xhr.open("POST", "./AdminInterface");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send();
	
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function showingBookDetails()
{
  d= 190;
  z = 890;
  document.getElementById("booksContainerInner").innerHTML="";
  for(let i=0; i<searchingBooksDetails.length; i++){
	if(i%5 == 0 || i==0)
	{
	d +=1;
	let a = document.createElement("div");
	document.getElementById("booksContainerInner").appendChild(a);
	a.setAttribute("id",d);
	a.setAttribute('class', "bookDet");
	creatingDivForEachBooks(i);
	}
	else{
		creatingDivForEachBooks(i);
	}
  }
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function fullBookDetails(isbnNo)
{
	document.getElementById("booksContainerInner").innerHTML="";	
	specificBookDetails=[];
	var BorrowBookId = 0;
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function()
	{
		if (xhr.readyState == 4 && this.status == 200) {
			
			var bigSizeBookImage = document.createElement("div");
			document.getElementById("booksContainerInner").appendChild(bigSizeBookImage);
			bigSizeBookImage.setAttribute('id',"bigSizeBookImage");
	
			var imgDiv = document.createElement("img");
			document.getElementById("bigSizeBookImage").appendChild(imgDiv);
			imgDiv.setAttribute("class", "BigImageSize");
			imgDiv.setAttribute("src", "y.png");
	
			var fullDetailsOfBook = document.createElement("div");
			document.getElementById("booksContainerInner").appendChild(fullDetailsOfBook);
			fullDetailsOfBook.setAttribute('id',"fullDetailsOfBook");
	
			u = 791;
			var responseObject = JSON.parse(this.responseText);
			var topicOfDetails =["Book Name","Author Name","ISBN Number","Pages of the Book","Published Date","Book Category"]
			var detailsArray = [responseObject[0].Book_Name,responseObject[0].Author_Name,responseObject[0].ISBN_No,responseObject[0].Pages,responseObject[0].Published_Date,responseObject[0].Category];
				console.log(responseObject[0].Book_Name);
				console.log(responseObject[0].Author_Name);
				BorrowBookId = responseObject[0].ISBN_No;
				console.log(responseObject[0].Pages);
				console.log(responseObject[0].Published_Date);
				console.log(responseObject[0].Category);
				console.log(responseObject[0].BorrowedBy);
			for(let i=0;i<6;i++)
			{
				u += 1;
				var eachInformationOfBook = document.createElement("div");
				document.getElementById("fullDetailsOfBook").appendChild(eachInformationOfBook);
				eachInformationOfBook.setAttribute('class',"eachInformationOfBook");
				eachInformationOfBook.setAttribute('id',u);		
				
				var topic = document.createElement("span");
				document.getElementById(u).appendChild(topic);
				var x = "topic"+u;
				topic.setAttribute("id", x);
				document.getElementById(x).innerText=topicOfDetails[i];
				
				var colon = document.createElement("span");
				document.getElementById(u).appendChild(colon);
				var y = "colon"+u;
				colon.setAttribute("id", y);
				document.getElementById(y).innerText=":";
				
				var information = document.createElement("span");
				document.getElementById(u).appendChild(information);
				var z = "information"+u;
				information.setAttribute("id", z);
				information.setAttribute("class","text16");
				document.getElementById(z).innerText=detailsArray[i];
				
			}
	
			var status = document.createElement("div");
			document.getElementById("booksContainerInner").appendChild(status);
			status.setAttribute('id',"status");
	
			for(let j=0;j<3;j++)
			{
				var statusOfBook = document.createElement("div");
				document.getElementById("status").appendChild(statusOfBook);
				statusOfBook.setAttribute('class',"statusOfBook");
				statusOfBook.setAttribute("id","status"+j);
			}
			
			document.getElementById("status0").innerText="Borrow Book";
			document.getElementById("status2").innerText="Cancel";
			document.getElementById("status0").style.backgroundColor="orange";
			document.getElementById("status0").style.border="1px solid orange";
			document.getElementById("status2").style.backgroundColor="lightgrey";
			document.getElementById("status2").style.border="1px solid lightgrey";
			document.getElementById("status0").addEventListener("mouseover",()=>{document.getElementById("status0").style.height="56px";document.getElementById("status0").style.width="125%";document.getElementById("status0").style.transition=".5s"})
			document.getElementById("status0").addEventListener("mouseout",()=>{document.getElementById("status0").style.height="40px";document.getElementById("status0").style.width="100%";document.getElementById("status0").style.transition=".5s"})
			document.getElementById("status2").addEventListener("mouseover",()=>{document.getElementById("status2").style.height="56px";document.getElementById("status2").style.width="125%";document.getElementById("status2").style.transition=".5s"})
			document.getElementById("status2").addEventListener("mouseout",()=>{document.getElementById("status2").style.height="40px";document.getElementById("status2").style.width="100%";document.getElementById("status2").style.transition=".5s";})
			document.getElementById("status2").addEventListener("click",searchingBooks)					
				
			 if(responseObject[0].Available_Count != 0 && responseObject[0].Boolean != "true")
             {
				document.getElementById("status0").addEventListener("click", () => {
					
					  var shadow = document.createElement("div");
                      UserContainer.appendChild(shadow);
                      shadow.setAttribute("id", "shadow");
                      
                      $("#shadow").animate({
           					 width: "toggle",
     				   });
     				   
     				  document.getElementById("shadow").innerHTML="";
     				  
     				  var bookborrowContainer = document.createElement("div");
        			  shadow.appendChild(bookborrowContainer);
        		      bookborrowContainer.setAttribute("id", "bookborrowContainer");
        		      
        		      var CloseButton = document.createElement("div");
        			  bookborrowContainer.appendChild(CloseButton);
        		      CloseButton.setAttribute("id", "CloseButton");
        		      CloseButton.innerHTML="&#215;";
        		      document.getElementById("CloseButton").addEventListener("click",()=>{
						    removingShadow()
							document.getElementById("UserContainer").removeChild(shadow);
					  })
        		      
        		      var headingofBorrow = document.createElement("span");
        			  bookborrowContainer.appendChild(headingofBorrow);
        		      headingofBorrow.setAttribute("id", "headingofBorrow");
        		      headingofBorrow.innerText="Borrow Details";
        		      
        		      var inputBox = document.createElement("div");
        			  bookborrowContainer.appendChild(inputBox);
        		      inputBox.setAttribute("class", "input-box1");
        		      inputBox.setAttribute("id", "size64");
        		      
        		      let todayDate = new Date();
        		      todayDate.setDate(todayDate.getDate())
        		      let current = todayDate.toISOString().substring(0,10)
        		      
    				  inputBox.innerHTML=`<form> <div class='input-box1' id="alignment20">
                     	   <input class="input1" type='Date' required id='ReturnDate' min='${current}' max='2023-06-30'>
                     	   <label class='label1' for=''>Return Date</label>
                     	  </div></form>`    		      
        		      
 	       		      var Borrow = document.createElement("div");
        			  bookborrowContainer.appendChild(Borrow);
        		      Borrow.setAttribute("id", "Borrow");
        		      Borrow.innerText="Borrow";
                       
                      
                      Borrow.addEventListener("click",()=>{
				         if(document.getElementById("ReturnDate").value!=""){
							 
							  document.getElementById("status0").innerText="Return Book";
							  document.getElementById("status0").style.backgroundColor="green";
						      document.getElementById("status0").style.border="1px solid green";
			 				  document.getElementById("status2").style.backgroundColor="lightgrey";
							  document.getElementById("status2").style.border="1px solid lightgrey";
							  BorrowBooks(BorrowBookId,document.getElementById("ReturnDate").value);
					     }
					 })
				})	
			}
			 else if(responseObject[0].Boolean == "true")
             {
				document.getElementById("status0").innerText="Return Book";
				document.getElementById("status0").style.backgroundColor="green";
				document.getElementById("status0").style.border="1px solid green";
			 	document.getElementById("status2").style.backgroundColor="lightgrey";
				document.getElementById("status2").style.border="1px solid lightgrey";
				document.getElementById("status0").addEventListener("click",() =>
				{
					returnpage(1);
				});
			 }
			
             else if(responseObject[0].Available_Count == 0 && responseObject[0].Boolean == "false")
             {
				document.getElementById("status0").innerText="Book Borrowed";
				document.getElementById("status0").style.backgroundColor="red";
				document.getElementById("status0").style.border="1px solid red";
				document.getElementById("status0").style.color="#232228";
			 }
		}
	}
	var ISBN_NoOfBook = {};
	ISBN_NoOfBook.NoOfTheBook = isbnNo;
	xhr.open("POST", "./filter/SearchBooks");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(ISBN_NoOfBook));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function creatingDivForEachBooks(i)
{
	var b = document.createElement("div");
	document.getElementById(d).appendChild(b);
	b.setAttribute('class', 'bookDesign');
	e = searchingBooksDetails[i].ISBN_No;
	b.setAttribute('id',e);
	b.setAttribute('value',e);
	b.addEventListener("click",(event) =>{
         if(event.target.classList=="bookDesign")
         {
			 fullBookDetails(event.target.id);
		 }
	});
	
 	var imgDiv = document.createElement("img");
	document.getElementById(e).appendChild(imgDiv);
	imgDiv.setAttribute("class", "imageSize1");
	imgDiv.setAttribute("src","y.png");
	imgDiv.addEventListener("click",(event) => {
		var id = event.target.parentElement.id;
	    fullBookDetails(id);
	});
	
	
	var bookName = document.createElement("span");
	document.getElementById(e).appendChild(bookName);
	z += 1;
	bookName.setAttribute("id",z);
	document.getElementById(z).innerText=searchingBooksDetails[i].Book_Name;
	bookName.addEventListener("click",(event) => {
		var id = event.target.parentNode.id;
	    fullBookDetails(id);
	});
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function IssuedBooks()
{
   BorrowBookDetails=[]; 
   document.getElementById("borrowPersonDetails").innerHTML="";
   var xhr = new XMLHttpRequest();
   xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4 && this.status == 200) {
	   var responseObject = JSON.parse(this.responseText);
	   
	   for(let i=0;i<responseObject.length;i++)
	   {
		   BorrowBookDetails.push(responseObject[i])
	   }
	
	   for(let i=0;i<BorrowBookDetails.length;i++)
	   { 
		   
		   if(i==7)
		   {
			   break;
		   }
		   
		    var divName = "Person"+i;
		    
		    var ParentDiv = document.createElement("span");
			document.getElementById("borrowPersonDetails").appendChild(ParentDiv);
			ParentDiv.setAttribute("class","elementsOfBorrowBooks");
			ParentDiv.setAttribute("id",divName)
		   
		   	var bookName = document.createElement("span");
			document.getElementById(divName).appendChild(bookName);
			bookName.innerText = responseObject[i].Book_Name;
			
		    var bookIsbnNO = document.createElement("span");
			document.getElementById(divName).appendChild(bookIsbnNO);
			bookIsbnNO.innerText = responseObject[i].ISBN_No;
			
			var personName = document.createElement("span");
			document.getElementById(divName).appendChild(personName);
			personName.innerText = responseObject[i].Name;
			
			var personPhNo = document.createElement("span");
			document.getElementById(divName).appendChild(personPhNo);
			personPhNo.innerText = responseObject[i].Phone_Number;
			
			var fromDate = document.createElement("span");
			document.getElementById(divName).appendChild(fromDate);
			fromDate.innerText = responseObject[i].From_Date;
			
			var returnDate = document.createElement("span");
			document.getElementById(divName).appendChild(returnDate);
			returnDate.innerText = responseObject[i].Return_Date;
			
			
			var bookFine = document.createElement("span");
			document.getElementById(divName).appendChild(bookFine);
			if(responseObject[i].Status == "NOTRETURNED")
			{
			   var currentUserOfTheBook = document.createElement("span");
			   document.getElementById(divName).appendChild(currentUserOfTheBook);
			   currentUserOfTheBook.setAttribute("id","currentUserOfTheBook");
			   currentUserOfTheBook.innerHTML="<i class='fa-solid fa-book'></i>";
			}
			
			if(responseObject[i].Fine>0)
			{
			   bookFine.innerText = responseObject[i].Fine;
			   bookFine.style.color="red";
			}
			else
			{
				bookFine.innerText = "-";
			}
	   }
	  }
    }
    var NameOfTheBook = {};
	NameOfTheBook.bookName = document.getElementById("borrowBookName").value;
    xhr.open("POST", "./BorrowBookDetails");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(NameOfTheBook));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function LMSUserDetails()
{
	   LMSUsers = [];
	   document.getElementById("PersonDetails").innerHTML="";
   		var xhr = new XMLHttpRequest();
   		xhr.onreadystatechange = function() {
	   if (xhr.readyState == 4 && this.status == 200) {
	   var responseObject = JSON.parse(this.responseText);
	   for(let i=0;i<responseObject.length;i++)
	   {
		   LMSUsers.push(responseObject[i])
	   }
	   for(let i=0;i<LMSUsers.length;i++)
	   { 
		   if(LMSUsers.length==TotalUser && i==7)
		   {
			   break;
		   }
		    var divName = responseObject[i].Phone_Number;
		    
		    var ParentDiv = document.createElement("div");
			document.getElementById("PersonDetails").appendChild(ParentDiv);
			ParentDiv.setAttribute("class","elementsOfUserDetails");
			ParentDiv.setAttribute("id",divName)
			ParentDiv.addEventListener("click",(event)=>{
				Phone_NumberOfUser = event.target.id;
				console.log(Phone_NumberOfUser.length);
				if(Phone_NumberOfUser.length == 0 ){
					Phone_NumberOfUser = event.target.parentElement.id;
					console.log(Phone_NumberOfUser);
				}
				getUserHistory();
			});
		   
           if(responseObject[i].Gender=="MALE")
           {
			var imgDiv = document.createElement("img");
			document.getElementById(divName).appendChild(imgDiv);
			imgDiv.setAttribute("class", "imageSize");
			imgDiv.setAttribute("src", "m.png");
		   }
		   else if(responseObject[i].Gender=="FEMALE")
		   {
			var imgDiv = document.createElement("img");
			document.getElementById(divName).appendChild(imgDiv);
			imgDiv.setAttribute("class", "imageSize");
			imgDiv.setAttribute("src", "f.png");		   
		   }
			
		    var Name = document.createElement("span");
			document.getElementById(divName).appendChild(Name);
			Name.innerText = responseObject[i].Name;
			
			var DateOfBirth = document.createElement("span");
			document.getElementById(divName).appendChild(DateOfBirth);
			DateOfBirth.innerText = responseObject[i].Date_Of_Birth;
			
			var personPhNo = document.createElement("span");
			document.getElementById(divName).appendChild(personPhNo);
			personPhNo.innerText = responseObject[i].Phone_Number;
			
			var TotalNumberOfBookIssued = document.createElement("span");
			document.getElementById(divName).appendChild(TotalNumberOfBookIssued);
			TotalNumberOfBookIssued.innerText = responseObject[i].TotalBooksIssued;
			
			var TotalFineOfUser = document.createElement("span");
			document.getElementById(divName).appendChild(TotalFineOfUser);
			if(responseObject[i].TotalFineOfUser>0)
			{
			   TotalFineOfUser.innerText = responseObject[i].TotalFineOfUser;
			   TotalFineOfUser.style.color="red";
			}
			else
			{
				TotalFineOfUser.innerText = "-";
			}
	   }
	  }
    }
    var UserDetails = {};
	UserDetails.UserName = document.getElementById("UserName").value;
    xhr.open("POST", "./AllUserInLibrary");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(UserDetails));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function searchTheUpdatedBook()
{
	console.log("sheik");
	$('#container6').hide();
	$('#backButtonSize2').hide();
	$('#container2').show();
	document.getElementById("UpdatedBookDetail").innerHTML="";
	$('#forBookDetailsUpdated').hide();
    $('#container2').show();
    var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function()
	{
		if (xhr.readyState == 4 && this.status == 200) {
			var responseObject = JSON.parse(this.responseText);
		    var topicOfDetails =["Book Name","Author Name","ISBN Number","Pages of the Book","Published Date","Book Category","Available Stock","Department","Floor","Shelf Number"];
		    var idOfSpanUpdatedDetails = ["BookName1","AuthorName1","IsbnNo1","pages1","PublishedDate1","category1","Available_Count1","Department1","Floor_No1","Shelve_Number1"];
		    var categoryOfBook = responseObject[0].Category.toLowerCase().split(" ");
			for (let i = 0; i < categoryOfBook.length; i++) {
 				   categoryOfBook[i] = categoryOfBook[i][0].toUpperCase() + categoryOfBook[i].substr(1);
			}
			categoryOfBook = categoryOfBook.join(" ");
			
			var floor = responseObject[0].Floor_Number.toLowerCase().split("floor");
 		    floor = floor[0][0].toUpperCase() + floor[0].substr(1);
			floor = floor+" "+"Floor";
			
		    var o = responseObject[0].Published_Date.split("/");
		    var formattedDate = o[2]+"-"+o[1]+"-"+o[0];
			var detailsArray = [responseObject[0].Book_Name,responseObject[0].Author_Name,responseObject[0].ISBN_No,responseObject[0].Pages,responseObject[0].Published_Date,categoryOfBook,responseObject[0].Available_Count,categoryOfBook,floor,responseObject[0].Shelve_No];
            console.log(detailsArray);
			for(let i=0;i<10;i++)
			{
				var v = i+9807;
				var eachInformationOfBook = document.createElement("div");
				document.getElementById("UpdatedBookDetail").appendChild(eachInformationOfBook);
				eachInformationOfBook.setAttribute('class',"eachInformationOfBook1");
				eachInformationOfBook.setAttribute('id',v);		
				
				var topic = document.createElement("span");
				document.getElementById(v).appendChild(topic);
				var x = "topic1"+v;
				topic.setAttribute("id", x);
				document.getElementById(x).innerText=topicOfDetails[i];
				
				var colon = document.createElement("span");
				document.getElementById(v).appendChild(colon);
				var y = "colon1"+v;
				colon.setAttribute("id", y);
				document.getElementById(y).innerText=":";
				
				var information = document.createElement("span");
				document.getElementById(v).appendChild(information);
				var z = "information1"+v;
				information.setAttribute("id", z);
				information.setAttribute("class","text16");
				document.getElementById(z).innerText=detailsArray[i];
				if(idOfSpanUpdatedDetails[i]=="PublishedDate1")
				{
					document.getElementById(idOfSpanUpdatedDetails[i]).value=formattedDate;
				}
				else{
					document.getElementById(idOfSpanUpdatedDetails[i]).value=detailsArray[i];
				}
				
			}
		}
	}
	var SearchBooks = {};
	SearchBooks.NoOfTheBook = document.getElementById("inputIsbnNo").value;
	xhr.open("POST", "./filter/SearchBooks");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(SearchBooks));
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function logOutFunction()
{
	var currentlyLoged = document.cookie.split("=");
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && this.status== 200)
		{
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Message == "Thankyou Visit Again")
			{
		       document.cookie = "Session_ID=";
			   location.reload(); 
			}
			else if(responseObject.Message == "Something Went Wrong")
			{
				console.log("Something Went Wrong");
			}
		}
	}
	
	xhr.open("POST","./LogOut");
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send("Session_ID="+currentlyLoged[1]);
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
function onLoadStartUp()
{
	var xhr = new XMLHttpRequest
	xhr.onreadystatechange = function()
	{
		if(xhr.readyState==4 && xhr.status==200)
		{
			console.log("coming out");
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Message=="Cookie is not Available")
			{
				console.log("coming in");
				SignInProcess();
			}
			else if (responseObject.Role=="ADMIN")
			{
				console.log("Admin");
				gender = responseObject.Gender;
				console.log(responseObject.Gender)
				AdminInterfaces();
				websocketFunction();
			}
			else if (responseObject.Role=="USER")
			{
				console.log("User");
				gender = responseObject.Gender;
				console.log(responseObject.Gender)
				UserInterfaces();
				websocketFunction();
			}
		}
	}
	xhr.open("POST","./filter/CurrentUser");
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send();
	
}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
 function forUpdatedSubmitButton()
 {
	 var xhr = new XMLHttpRequest();
	 xhr.onreadystatechange = function()
	 {
		 if (xhr.readyState == 4 && this.status == 200) {
			var responseObject = JSON.parse(this.responseText);
			
			if(responseObject.Message == "Invalid Input Please Check it")
			{
				alert("Invalid Inputs Please Check it");
			}
			else{
			  AddedBookDetails =[responseObject.Book_Name,responseObject.Author_Name,responseObject.ISBN_No,responseObject.Pages,responseObject.Published_Date,responseObject.Category,responseObject.Available_Count,responseObject.Category,responseObject.Floor_No,responseObject.Shelve_Number];
			  $('#container2').hide();
			  $('#container6').hide();
			  $('#forBookDetailsUpdated').show();
			  alldetailsOfAddBooksAndUpdatedBooks("UpdatedBookDetails1");
			  document.getElementById("UpdatedBookDetail").innerHTML="";
			  document.getElementById("inputIsbnNo").value = "";
			}
		}
/*		document.getElementById("IsbnNo1").value="";
		document.getElementById("BookName1").value="";
		document.getElementById("AuthorName1").value="";
		document.getElementById("pages1").value="";
		document.getElementById("PublishedDate1").value="";
		document.getElementById("category1").value="None";*/
		//searchTheUpdatedBook();
	 }
	 
	 var UpdatedBookDetails ={};
	 UpdatedBookDetails.isbnNo=document.getElementById("IsbnNo1").value;
     UpdatedBookDetails.bookName = document.getElementById("BookName1").value;
     UpdatedBookDetails.authorName = document.getElementById("AuthorName1").value;
	 UpdatedBookDetails.pages = document.getElementById("pages1").value;
	 UpdatedBookDetails.publishedDate = document.getElementById("PublishedDate1").value;
     UpdatedBookDetails.category = document.getElementById("category1").value;
     UpdatedBookDetails.availableStock = document.getElementById("Available_Count1").value;
     UpdatedBookDetails.floorNo = document.getElementById("Floor_No1").value;
     UpdatedBookDetails.department = document.getElementById("Department1").value;
     UpdatedBookDetails.shelveNo = document.getElementById("Shelve_Number1").value;
     console.log(document.getElementById("inputIsbnNo").value);
     UpdatedBookDetails.originalIsbnNo = document.getElementById("inputIsbnNo").value;
     
	 xhr.open("POST","./UpdateBookDetails");
	 xhr.setRequestHeader("Content-Type", "application/json");
	 xhr.send(JSON.stringify(UpdatedBookDetails));
 }

function alldetailsOfAddBooksAndUpdatedBooks(sr)
{
	console.log("true");
	$('#container5').hide();
	$('#container').show();
	var topicOfDetails =["Book Name","Author Name","ISBN Number","Pages of the Book","Published Date","Book Category","Available Stock","Department","Floor","Shelf Number"];
	for(let i=0;i<AddedBookDetails.length;i++)
	{
	var v = i+3190;
	var eachInformationOfBook = document.createElement("div");
	document.getElementById(sr).appendChild(eachInformationOfBook);
	eachInformationOfBook.setAttribute('class',"eachInformationOfBook1");
	eachInformationOfBook.setAttribute('id',v);		
				
	var topic = document.createElement("span");
	document.getElementById(v).appendChild(topic);
	var x = "topic2"+v;
	topic.setAttribute("id", x);
	document.getElementById(x).innerText=topicOfDetails[i];
				
	var colon = document.createElement("span");
	document.getElementById(v).appendChild(colon);
	var y = "colon2"+v;
	colon.setAttribute("id", y);
	document.getElementById(y).innerText=":";
				
	var information = document.createElement("span");
	document.getElementById(v).appendChild(information);
	var z = "information2"+v;
	information.setAttribute("id", z);
	information.setAttribute("class","text16");
	document.getElementById(z).innerText=AddedBookDetails[i];
	}
}


function ParticularPersonBorrowBooks()
{
     	ParticularPersonBorrowBooksDetails=[];
     	document.getElementById("memberswholeDiv").style.display="none";
     	
     	
}

function UserInterfaces()
{
	document.getElementById("fullContainer").innerHTML="";
	//$(".summa").hide();
	$(".summa1").hide();
	$('#UserContainer').show();
	if(gender=="MALE")
	{
			var imgDiv = document.createElement("img");
			document.getElementById("profileImage").appendChild(imgDiv);
			imgDiv.setAttribute("id", "imgSize");
			imgDiv.setAttribute("src","m.png");
	}
    else if(gender=="FEMALE")
	{
			var imgDiv = document.createElement("img");
			document.getElementById("profileImage").appendChild(imgDiv);
			imgDiv.setAttribute("id", "imgSize");
			imgDiv.setAttribute("src","f.png");
	}
	allBookss();
	document.getElementById("allBook").addEventListener("click", () => {
		global = 2;
		changeColor("allBook");
		allBookss();
	})
	document.getElementById("ReturnBooks").addEventListener("click", () => {
		global = 2;
		changeColor("ReturnBooks");
		WantToBeReturnBooks();
	})
	document.getElementById("History").addEventListener("click", () => {
		global = 2;
		changeColor("History");
		getUserHistory();
	})
	document.getElementById("terms").addEventListener("click", () => {
		global = 2;
		changeColor("terms");
		termsAndConditions();
	})
	document.getElementById("settings").addEventListener("click", () => {
		global = 2;
		changeColor("settings");
		settings();
	})
}

function getUserHistory()
{
	$('.summa').hide();
	$('#HistorywholeDiv').show();
	document.getElementById("UserHistoryDetails").innerHTML="";
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function()
	{
		if(xhr.readyState == 4 &&  this.status == 200)
		{
			console.log("sri")
			var responseObject = JSON.parse(this.responseText);
			
			console.log(responseObject)
			if(responseObject.length<1)
			{
				console.log("No History")
			}
			else{
			for(let i=0;i<responseObject.length;i++)
			{	
			var divName = "UserHistory"+i;
		    console.log("sri")
		    var ParentDiv = document.createElement("div");
			document.getElementById("UserHistoryDetails").appendChild(ParentDiv);
			ParentDiv.setAttribute("class","elementsOfUserHistory");
			ParentDiv.setAttribute("id",divName)
			
			var bookName = document.createElement("span");
			document.getElementById(divName).appendChild(bookName);
			bookName.innerText = responseObject[i].Book_Name;
			
		    var bookIsbnNO = document.createElement("span");
			document.getElementById(divName).appendChild(bookIsbnNO);
			bookIsbnNO.innerText = responseObject[i].ISBN_No;
			
						var fromDate = document.createElement("span");
			document.getElementById(divName).appendChild(fromDate);
			fromDate.innerText = responseObject[i].From_Date;
			
			var returnDate = document.createElement("span");
			document.getElementById(divName).appendChild(returnDate);
			returnDate.innerText = responseObject[i].Return_Date;
			
			
			var bookFine = document.createElement("span");
			document.getElementById(divName).appendChild(bookFine);
			bookFine.setAttribute("class","alignment50");
			if(responseObject[i].Fine>0)
			{
			   bookFine.innerText = responseObject[i].Fine;
			}
			else
			{
				bookFine.innerText = "-";
			}
			
		    var finedDate = document.createElement("span");
			document.getElementById(divName).appendChild(finedDate);
			finedDate.setAttribute("class","alignment50");
			if(responseObject[i].Fined_Date)
			{
				finedDate.innerText = responseObject[i].Fined_Date;
			}
			else{
				finedDate.innerText = "-";
			}
			
			var Status = document.createElement("span");
			document.getElementById(divName).appendChild(Status);
			Status.setAttribute("class","alignment50");
			if(responseObject[i].Status == "RETURNED")
			{
			   Status.innerText = "RETURNED";
			   Status.style.color="green";
			}
			else
			{
			   Status.innerText = "NOT RETURNED";
			   Status.style.color="red";
			}
		  }
		}
	  }
    }
     xhr.open("POST","./filter/User_History");
	 xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 xhr.send("Phone_Number="+Phone_NumberOfUser);
}

function BorrowBooks(BorrowBookId,ReturnDate){
	
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		
		if(xhr.readyState == 4 && this.status==200)
		{	
			fullBookDetails(BorrowBookId+"")
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Status == "Something went wrong")
			{
				 alert("Book already borrowed sorry!");
				 document.getElementById("UserContainer").removeChild(shadow);
			}
			else{
			document.getElementById("ReturnDate").value="";
			console.log(responseObject);
			if(BorrowBookId == undefined){
				alert("Book already borrowed sorry!");
				return;
			}
			alert("ISBN Number = "+BorrowBookId+" Book is Borrowed Successfully"+"\n"+"Floor : "+responseObject.Floor_No+"\n"+"Department : "+responseObject.Department+"\n"+"Shelf Number : "+responseObject.Shelve_Number)
		    document.getElementById("UserContainer").removeChild(shadow);
			}
		}
		
	}
	
	 var ISBN_No = BorrowBookId;
	 var Return_Date = ReturnDate;
	 xhr.open("POST","./filter/BorrowBooks");
	 xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 xhr.send("ISBN_No="+ISBN_No+"&Return_Date="+Return_Date);	
}

function WantToBeReturnBooks(){
	
	$('.summa').hide();
	$('#ReturnBookDetail').show();
	document.getElementById("returnBooksDetails").innerHTML=""
	var name0 ="";
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function(){
		
	    var topicOfDetails =["Book Name","ISBN Number","Date Borrowed","Return Date"];
		if(xhr.readyState == 4 && this.status==200)
		{
			var responseObject = JSON.parse(this.responseText);
			console.log(responseObject);
            for(let i=0;i<responseObject.length;i++)
            {
			
			if(i%2==0)
			{
			name0 = 'twoBookDiv'+i
			var twoBookDiv = document.createElement("div");
			document.getElementById("returnBooksDetails").appendChild(twoBookDiv);
			twoBookDiv.setAttribute("class","twoBookDiv");
			twoBookDiv.setAttribute("id",name0);
			}
			
			var name = 'returnBookOfeachBookDiv'+i
			var returnBookOfeachBookDiv = document.createElement("div");
			document.getElementById(name0).appendChild(returnBookOfeachBookDiv);
			returnBookOfeachBookDiv.setAttribute("class","returnBookOfeachBookDiv");
			returnBookOfeachBookDiv.setAttribute("id",name);
			
			var name1 = 'ImgWholeDiv'+i
		    var ImgWholeDiv = document.createElement("div");
			document.getElementById(name).appendChild(ImgWholeDiv);
			ImgWholeDiv.setAttribute("class","ImgWholeDiv");
			ImgWholeDiv.setAttribute("id",name1);
			
			var name2 = 'returnDetailsWholeDiv'+i
			var returnDetailsWholeDiv = document.createElement("div");
			document.getElementById(name).appendChild(returnDetailsWholeDiv);
			returnDetailsWholeDiv.setAttribute("class","returnDetailsWholeDiv");
			returnDetailsWholeDiv.setAttribute("id",name2);
			
			var imgDiv = document.createElement("img");
			document.getElementById(name1).appendChild(imgDiv);
			imgDiv.setAttribute("class", "ImgDiv");
			imgDiv.setAttribute("src", "y.png");
			
			var name3 = 'returnDetailsDiv'+i
			var returnDetailsDiv = document.createElement("div");
			document.getElementById(name2).appendChild(returnDetailsDiv);
			returnDetailsDiv.setAttribute("class","returnDetailsDiv");
			returnDetailsDiv.setAttribute("id",name3); 
			
			var ram = [responseObject[i].Book_Name,responseObject[i].ISBN_No,responseObject[i].From_Date,responseObject[i].Return_Date];
			for(let j=0;j<4;j++)
			{
			var v = responseObject[i].ISBN_No*(Math.random());	
			
            var wholeDetailsDiv = document.createElement("div");
            document.getElementById(name3).appendChild(wholeDetailsDiv);
            wholeDetailsDiv.setAttribute("class","wholeDetailsDiv")
            wholeDetailsDiv.setAttribute("id", v);
			
			var topic = document.createElement("span");
			document.getElementById(v).appendChild(topic);
			var x = "topic2"+v+responseObject[i].Book_Name;
			topic.setAttribute("id", x);
			topic.setAttribute("class","text87");
			document.getElementById(x).innerText=topicOfDetails[j];
				
			var colon = document.createElement("span");
			document.getElementById(v).appendChild(colon);
			var y = "colon2"+v+responseObject[i].Book_Name;
			colon.setAttribute("id", y);
			colon.setAttribute("class","text87");
			document.getElementById(y).innerText=":";
				
			var information = document.createElement("span");
			document.getElementById(v).appendChild(information);
			var z = "information2"+v+responseObject[i].Book_Name;
			information.setAttribute("id", z);
			information.setAttribute("class","text16");
			document.getElementById(z).innerText=ram[j];
			
			}
			
			var name4 = 'returnBookButtonDiv'+i
			var returnBookButtonDiv = document.createElement("div");
			document.getElementById(name2).appendChild(returnBookButtonDiv);
			returnBookButtonDiv.setAttribute("class","returnBookButtonDiv");
			returnBookButtonDiv.setAttribute("id",name4);
			
			var name5 = 'returnButton'+i
			var returnButton = document.createElement("div");
			document.getElementById(name4).appendChild(returnButton);
			returnButton.setAttribute("class","returnButton");
			returnButton.innerText="Return Book";
			returnButton.setAttribute("id",name5);
			if(responseObject[i].isFined===true)
			{
				document.getElementById(name5).style.backgroundColor="red";
				var Book_Fine = {};
				Book_Fine.Book_ISBN_No = responseObject[i].ISBN_No;
				Book_Fine.Fine_Amount = responseObject[i].Fine;
				AmountOfFine.push(Book_Fine);
				document.getElementById(name5).addEventListener("click",(event)=>{
					
					var isbnNo = event.target.parentElement.parentElement.childNodes[0].childNodes[1].childNodes[2].innerText;
					BankDetails(isbnNo)
				});
			}
			else{
				returnButton.addEventListener("click",() =>
				{
					returnpage(2)
				});
			}
  	    }
  	
  	  }
 }
	 xhr.open("POST","./filter/ToBeReturnBooks");
	 xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	 xhr.send();	
}



 function removingShadow() {

     $("#shadow").animate({
         width: "toggle",
      });
 }

function forConform(Role)
{	
	if(Role == "USER")
	{
	  var RoleID = "UserContainer";	
	}
	else{
	  var RoleID = "fullContainer";
	}
    
    var shadow = document.createElement("div");
    document.getElementById(RoleID).appendChild(shadow);
    shadow.setAttribute("id", "shadow");
                      
    $("#shadow").animate({
            width: "toggle",
     });
     
    document.getElementById("shadow").innerHTML="";
    
    
    var bookborrowContainer = document.createElement("div");
    shadow.appendChild(bookborrowContainer);
    bookborrowContainer.setAttribute("id", "bookborrowContainer");
    document.getElementById("bookborrowContainer").style.height="306px";
	document.getElementById("bookborrowContainer").style.margin =" 388px auto";
	
	var CloseButton = document.createElement("div");
    bookborrowContainer.appendChild(CloseButton);
    CloseButton.setAttribute("id", "CloseButton");
    CloseButton.innerHTML="&#215;";
    document.getElementById("CloseButton").addEventListener("click",()=>{
		
		    document.getElementById(RoleID).removeChild(shadow);
		    settings();
	});
    
	
	var headingofBorrow = document.createElement("span");
	bookborrowContainer.appendChild(headingofBorrow);
	headingofBorrow.setAttribute("id", "headingofBorrow");
	headingofBorrow.innerText = "Confirmation";
	var inputBox = document.createElement("div");
	
	bookborrowContainer.appendChild(inputBox);
	inputBox.setAttribute("class", "input-box1");
	inputBox.setAttribute("id", "size64");
	document.getElementById("size64").style.marginTop = "47px";
	
	inputBox.innerHTML = `<form id="confirm">        
                     	  <div class='input-box1' id='pass7'>
                      		<input class="input1" type='password' required id='ConformPassword3' autocomplete="off">
                      		<i class='fa-regular fa-eye-slash' id='showingPassword3' onclick="showingPassword('ConformPassword3','pass7','showingPassword3')"></i>
                      		<label class="label1" for=''>Password</label></div>
                          </form>`
        		      
        		      
     var return1= document.createElement("div");
     bookborrowContainer.appendChild(return1);
     return1.setAttribute("id", "return1");
     return1.innerText="Submit";
     document.getElementById("return1").addEventListener("click",()=>{
        var xhr = new XMLHttpRequest
	   xhr.onreadystatechange = function()
      {
		if(xhr.readyState==4 && xhr.status==200)
		{
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Password==document.getElementById("ConformPassword3").value)
			{
				console.log(document.getElementById("ConformPassword3").value);
				document.getElementById(RoleID).removeChild(shadow);
				 document.getElementById("UpdateDetailsContainer").style.display="block";
			}
			else{
				alert("Please enter correct Passord");
			}
		}
	}
	xhr.open("POST","./filter/CurrentUser");
	xhr.setRequestHeader("Content-type","application/x-www-form-urlencoded");
	xhr.send();
	});
}

function UpdateUserDetails()
{
	console.log(true);
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Message=="Successfull")
			{
				alert("Your Details are Updated");
			}
			else if(responseObject.Message == "Something went wrong" || responseObject.Message =="User is not Found")
			{
				console.log("Something went wrong");
			}
		}
	}
	var User_Details = {};
	User_Details.First_Name = document.getElementById("FirstName1").value;
	User_Details.Last_Name = document.getElementById("LastName1").value;
	User_Details.Date_Of_Birth = document.getElementById("DateOfBirth1").value;
	User_Details.Phone_Number = document.getElementById("PhoneNumber1").value;
	User_Details.Password = document.getElementById("password2").value;
	User_Details.Gender = "Male"
	xhr.open("POST", "./filter/UpdateUserDetails");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(User_Details));
}

function returnpage(Page)
{
	var shadow = document.createElement("div");
    UserContainer.appendChild(shadow);
    shadow.setAttribute("id", "shadow");
                      
    $("#shadow").animate({
            width: "toggle",
     });
     
    document.getElementById("shadow").innerHTML="";
    
    var bookborrowContainer = document.createElement("div");
    shadow.appendChild(bookborrowContainer);
    bookborrowContainer.setAttribute("id", "bookborrowContainer");
        		      
    var CloseButton = document.createElement("div");
    bookborrowContainer.appendChild(CloseButton);
    CloseButton.setAttribute("id", "CloseButton");
    CloseButton.innerHTML="&#215;";
    
    document.getElementById("CloseButton").addEventListener("click", () => {
    removingShadow()
    document.getElementById("UserContainer").removeChild(shadow);
	})

	var headingofBorrow = document.createElement("span");
	bookborrowContainer.appendChild(headingofBorrow);
	headingofBorrow.setAttribute("id", "headingofBorrow");
	headingofBorrow.innerText = "Confirmation";
	var inputBox = document.createElement("div");
	bookborrowContainer.appendChild(inputBox);
	inputBox.setAttribute("class", "input-box1");
	inputBox.setAttribute("id", "size64");

	inputBox.innerHTML = `<form id="conform">        
        		    	  <div class='input-box1'>
                     	   <input class="input1" type='number' required id='ISBN_no' autocomplete="off">
                     	   <label class="label1" for=''>ISBN Number</label>
                     	  </div>
                     	  <div class='input-box1' id='pass4'>
                      		<input class="input1" type='password' required id='ConformPassword' autocomplete="off">
                      		<i class='fa-regular fa-eye-slash' id='showingPassword' onclick="showingPassword('ConformPassword','pass4','showingPassword')"></i>
                      		<label class="label1" for=''>Password</label></div>
                          </form>`
        		      
        		      
     var return1= document.createElement("div");
     bookborrowContainer.appendChild(return1);
     return1.setAttribute("id", "return1");
     return1.innerText="Return";
     document.getElementById("return1").addEventListener("click",()=>{
		 returnBooks(Page);
	});
}

function returnBooks(Page)
{
		var xhr = new XMLHttpRequest();
	    xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Message=="Successfull")
			{
				if(Page == 1)
				{
					 alert("Book is Returned, Thank you");
					 document.getElementById("UserContainer").removeChild(shadow);
					 console.log("true123")
					 fullBookDetails(document.getElementById("ISBN_no").value);
				}
				else if(Page == 2)
				{
					alert("Book is Returned, Thank you");
					console.log("true123")
					document.getElementById("UserContainer").removeChild(shadow);
					WantToBeReturnBooks();
				}
				
			}
			else if(responseObject.Message == "Something went wrong" || responseObject.Message =="User is not Found")
			{
				alert("Something went wrong");
			}
		}
	}
	xhr.open("POST", "./filter/ReturnBooks");
	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	xhr.send("Password="+document.getElementById("ConformPassword").value+"&ISBN_No="+document.getElementById("ISBN_no").value);
}
function BankDetails(isbnNo)
{
	var shadow = document.createElement("div");
    UserContainer.appendChild(shadow);
    shadow.setAttribute("id", "shadow");
                      
    $("#shadow").animate({
            width: "toggle",
     });
     
    document.getElementById("shadow").innerHTML="";
    
    var bookborrowContainer = document.createElement("div");
    shadow.appendChild(bookborrowContainer);
    bookborrowContainer.setAttribute("id", "bookborrowContainer");
    document.getElementById("bookborrowContainer").style.height="590px"; 
    document.getElementById("bookborrowContainer").style.width="444px";
    document.getElementById("bookborrowContainer").style.margin = "248px auto";
    
    var CloseButton = document.createElement("div");
    bookborrowContainer.appendChild(CloseButton);
    CloseButton.setAttribute("id", "CloseButton");
    CloseButton.innerHTML="&#215;";
    document.getElementById("CloseButton").style.left="405px";
    
    document.getElementById("CloseButton").addEventListener("click", () => {
    removingShadow()
    document.getElementById("UserContainer").removeChild(shadow);
	})

	var headingofBorrow = document.createElement("span");
	bookborrowContainer.appendChild(headingofBorrow);
	headingofBorrow.setAttribute("id", "headingofBorrow");
	headingofBorrow.innerText = "Payment";
	var inputBox = document.createElement("div");
	bookborrowContainer.appendChild(inputBox);
	inputBox.setAttribute("class", "input-box1");
	inputBox.setAttribute("id", "size64");

	inputBox.innerHTML = `<form id="aligning"> 
						   <div class='input-box1'>
                     	   <input class="input1" type='number' id='ISBN_no' autocomplete="off" readonly>
                     	   <label class="label2" for=''>ISBN Number</label>
                     	  </div>   
                     	  <div class='input-box1'>
                     	   <input class="input1" type='number' id='Fine' autocomplete="off" readonly>
                     	   <label class="label2" for=''>Amount of fine</label>
                     	  </div>
                     	  <div class='input-box1'>
                     	   <input class="input1" type='number' required id='CreditCardNumber' autocomplete="off">
                     	   <label class="label1" for=''>Credit Card Number</label>
                     	  </div>
                     	  <div class='input-box1' id='pass5'>
                      		<input class="input1" type='password' required id='cvvNumber' maxlength="3" minlength="3" autocomplete="off">
                      		<i class='fa-regular fa-eye-slash' id='showingPassword1' onclick="showingPassword('cvvNumber','pass5','showingPassword1')"></i>
                      		<label class="label1" for=''>CVV Number</label></div>
                      		<div class='input-box1' id='pass4'>
                      		<input class="input1" type='password' required id='ConformPassword' autocomplete="off">
                      		<i class='fa-regular fa-eye-slash' id='showingPassword' onclick="showingPassword('ConformPassword','pass4','showingPassword')"></i>
                      		<label class="label1" for=''>Password</label></div>
                          </form>`
        		      
     console.log(AmountOfFine);	  
     for(var a of AmountOfFine)
     {
		 if(a.Book_ISBN_No == isbnNo){
			 
			 document.getElementById("ISBN_no").value = a.Book_ISBN_No; 
			 document.getElementById("Fine").value = a.Fine_Amount; 
		 }
	 }    
     var return1= document.createElement("div");
     bookborrowContainer.appendChild(return1);
     return1.setAttribute("id", "return1");
     return1.innerText="Pay";
     document.getElementById("return1").addEventListener("click",()=>{
	 PayingFine();
  });
}

function PayingFine()
{
    var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (this.readyState == 4) {
			var responseObject = JSON.parse(this.responseText);
			if(responseObject.Message=="Successfull")
			{
				alert("Fine Payed Successfully");
				document.getElementById("UserContainer").removeChild(shadow);
				WantToBeReturnBooks();
				document.getElementById("ISBN_no").value = "";
	            document.getElementById("Fine").value = "";
	   			document.getElementById("CreditCardNumber").value = "";
				document.getElementById("cvvNumber").value = "";
				document.getElementById("ConformPassword").value = "";
			}
			else if(responseObject.Message == "Something went wrong" || responseObject.Message =="User is not Found")
			{
				alert("Something went wrong please Check the inputs");
			}
		}
	}
	var Return_Details = {};
	Return_Details.ISBN_No = document.getElementById("ISBN_no").value;
	Return_Details.Fine = document.getElementById("Fine").value;
	Return_Details.CreditCardNumber = document.getElementById("CreditCardNumber").value;
	Return_Details.cvvNumber = document.getElementById("cvvNumber").value;
	Return_Details.ConformPassword = document.getElementById("ConformPassword").value;
	xhr.open("POST", "./filter/BankDetails");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.send(JSON.stringify(Return_Details));
}

function nextPage()
{
	$('#container').hide();
	$('#backButtonSize1').show();
	$('#container5').show();
}

function previous()
{
	$('#container5').hide();
	$('#backButtonSize1').hide();
	$('#container').show();
}
function nextPage1()
{
	$('#container2').hide();
	$('#backButtonSize2').show();
	$('#container6').show();
}

function previous1()
{
	$('#container6').hide();
	$('#backButtonSize2').hide();
	$('#container2').show();
}

			












