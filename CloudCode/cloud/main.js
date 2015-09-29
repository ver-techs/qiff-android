Parse.Cloud.afterSave("ChatItem", function(request) {
  sendRefreshNotification();
});
Parse.Cloud.afterSave("FixtureItem", function(request) {
  sendRefreshNotification();
});
Parse.Cloud.afterSave("LiveCommentaryItem", function(request) {
  sendRefreshNotification();
});
Parse.Cloud.afterSave("PointsTableItem", function(request) {
  sendRefreshNotification();
});

function sendRefreshNotification ( ) {
  var pushQuery = new Parse.Query(Parse.Installation);
  Parse.Push.send({
      where: pushQuery,
      data: {
          alert: "Thou shall refresh!"
      }
  }, {
      success: function() {
          // Push successful
      },
      error: function(error) {
          throw "Got an error " + error.code + " : " + error.message;
      }
  });
}
