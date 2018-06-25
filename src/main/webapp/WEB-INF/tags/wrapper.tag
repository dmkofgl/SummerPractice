<%@tag description="Wrapper Tag" pageEncoding="UTF-8"%>
<html><body>
<header>

<form action="/LogoutServlet" method="post">
<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAADIAAAAyCAYAAAAeP4ixAAAAAXNSR0IArs4c6QAAAARnQU1BAACxjwv8YQUAAAAJcEhZcwAADsMAAA7DAcdvqGQAAAKPSURBVGhD7dpdiA1hHMfx4zUiipQVSblQkgt3IpQ2KXdIiiuRKCUXXsuFC3kpUQoXpBQXLrikKKXYVkl2byjlrbyGC3nn+xv71LNznn3mzD6zy9T/V5+2mTNnZn5z5pmz9ZyGpQYZggf4jc94jxfowBWcwCbMxxhUlWlYgT04h2vowlvoHH7gJ7ahpYyHSrTiO25hH2ahTEZAJ34aTxDaf8gFtJQyRfLuYi2Go69MwSG8QWgfRQaliNONBfAzEnuh2zX0nlYNahH5hYNQpsKNu1SFRXTFlKqKiE5e0acTer0/XBGNsV6ZiMv4mi3Vp4jG4itszpaIXnAbKXUqomXdwou04mPPClHqVkT0FOy1kVLHIie1wt9IsSIBViTCiqSwIhFWJIUVibAiKaxIhBVJYUUirEgKKxJhRVJYkQgrksKKRFiRFP+siOYPNS/or0sxEEXOQ1kPt66pyEytIJoTvIrrPTrxEK/hb1+kv0W+4Bnu4TbceVyCJmGVw3DbNxXRhkMRi+YH27AYG3EMd/AN/r6kqIgujGaKdeFWYy50axdlBj7A7aepiOgKbMUqtGMpFmIOJmMYQhmN5TgDd5BQkfvYjdnoK9rXdMyDji+aAV6DA9B0tdufBIsU0Vz3S9zEKWyBrqRfUONMF+NGtvS3iLbXXz+6+suwH7obVPITQseNyYpoxif0YlnvoGJL4G5PNy/p/ioTsAEqqYsS2ldZWZHH3oqqPMJK+BmHI9BADr0nxQ401qGqTyVvJ5Sx0C8kQtuk0tNNn3IWDeaj0K2RQoM7fyA90Z7m1slFhPZRxi5oVrryTIK+b/InnafB/d9HT6Kz0A9u8gWeQ7dyraL/ErbjODR9rO+AURiANBp/AJQtHbphyTxZAAAAAElFTkSuQmCC">
<span>ReadIT</span>
<div style="position:absolute;right:0; top:0">
<span align=left> ${sessionScope.user.login}</span>
<input type="submit" value="Logout" >
</div>
</form>
</header>
</header>
  <jsp:doBody/>
</body></html>