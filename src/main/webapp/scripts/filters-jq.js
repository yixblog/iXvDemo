var ExpandBar = function (parentNode) {
    this.init = function () {
        parentNode.empty();
        var types = iXvChart.getTypesInUse();
        console.log(types);
        for (var i = 0; i < types.length; i++) {
            var typeName = types[i];
            var typeImage = iXvChart.getChart().getItemTypeImage(typeName);
            var typeDisplayName = iXvChart.getChart().getItemTypeDisplayName(typeName);
            var tr = $("<tr></tr>").appendTo(parentNode);
            var td = $("<td></td>", {"class": "filterButton selected",title:typeDisplayName}).appendTo(tr);
            $("<img>", {src: typeImage, alt: typeDisplayName, width: 22, height: 22}).appendTo(td);
            bindActions(td, typeName);
        }
    };

    function bindActions(tdObj, typeName) {
        tdObj.hover(function () {
            toggleButton($(this), "up");
        }, function () {
            toggleButton($(this), null);
        });
        tdObj.mousedown(
            function () {
                toggleButton($(this), "down");
            }
        ).mouseup(
            function () {
                toggleButton($(this), null);
            }
        );
        tdObj.click(function () {
            filterClick($(this), typeName);
        });
    }

    function filterClick(objTD, catType) {
        //Perform Filter Button Filtering (unless disabled)
        if (objTD.hasClass("disabled")) {
            return;
        }
        //generated
        showSelectState(objTD, iXvChart.toggleTypeFilter(catType)); //Indicate state on Filter Button
    }

    function toggleButton(objTD, attach) {
        if (attach != null) {
            if (attach.toLowerCase() == "up" || attach.toLowerCase() == "down") {
                attach = attach.substr(0, 1).toUpperCase() + attach.substr(1).toLowerCase();
            } else {
                attach = null;
            }
        }
        if (objTD.hasClass("disabled"))
            return;
        var selected = objTD.hasClass("selected");
        objTD.removeAttr("class");
        objTD.addClass("filterButton" + attach);
        if (selected) {
            objTD.addClass("selected");
        }
    }

    function showSelectState(objTD, state) {
        if (objTD.hasClass("disabled")) {
            return;
        }
        objTD.removeAttr("class").addClass("filterButton");
        if (state) {
            objTD.addClass("selected");
        }
    }
};
