Query = {
    /**
     * HTML Element
     */
    $: function (id) {
        return document.getElementById(id);
    },

    /**
     * Form Element
     */
    form: function (tag, name) {
        if (tag === undefined || tag === "") {
            console.error("tag can't be undefined or empty");
            return;
        }

        if (name === undefined || name === "") {
            console.error("name can't be undefined or empty");
            return;
        }

        let elements = document.getElementsByTagName(tag);
        if (elements === undefined || elements.length === 0) {
            return;
        }

        for (let i in elements) {
            if (elements[i] === undefined) {
                continue;
            }

            if (elements[i].name === name) {
                return elements[i];
            }
        }
    }

};
