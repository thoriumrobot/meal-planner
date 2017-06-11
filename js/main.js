const INGREDIENTS = [
    ['tomatoes', 'potatoes'],
    ['jam', 'ham'],
    ['pork', 'milk'],
    ['cheese', 'ketchup'],
    ['basil'],
    ['chocolate']
];

var selectedMeals = [];

/**
 * Create the ingredient list for a list of selected meals (int[]).
 */
function createIngredientsList(selectedMeals) {
    var ingredientsList = [];

    selectedMeals.forEach(function(mealId) {
        var mealIngredients = INGREDIENTS[mealId-1];
        Array.prototype.push.apply(ingredientsList, mealIngredients);
    });

    return ingredientsList;
}

function renderIngredientsList(ingredientsList) {
    const $ingredientsListElement = $('.js--ingredients-list--list');
    $ingredientsListElement.empty();

    ingredientsList.forEach(function(ingredient) {
        var $listItem = $('<li>' + ingredient + '</li>');
        $ingredientsListElement.append($listItem);
    });
}

$('.meal-list--entry').draggable({
    classes: {
        'ui-draggable-dragging': 'meal-plan--entry--dragging'
    },
    snap: '.meal-plan--entry-drop-zone',
    revert: 'invalid'

});

$('.meal-plan--entry-drop-zone').droppable({
    classes: {
        'ui-droppable-hover': 'meal-plan--entry-drop-zone--hover',
        'ui-droppable-active': 'meal-plan--entry-drop-zone--active'
    },
    drop: function(event, ui) {
        const droppedMeal = ui.draggable;
        const mealId = $(droppedMeal).data('mealId');

        selectedMeals.push(mealId);
        const ingredientsList = createIngredientsList(selectedMeals);
        renderIngredientsList(ingredientsList);
    }
});
