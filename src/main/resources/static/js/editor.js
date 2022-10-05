const executeCodeBtn = document.querySelector('.editor__run');
const resetCodeBtn = document.querySelector('.editor__reset');

console.log('hello world from index.js');

const editor = ace.edit("editor");
editor.setTheme("ace/theme/twilight");
editor.session.setMode("ace/mode/javascript");
editor.setValue("console.log('hello world from BNTA');");

editor.setOptions({
    fontFamily: 'Inconsolata',
    fontSize: '12pt',
    enableBasicAutocompletion: true,
    enableLiveAutocompletion: true,
});

// Events
//executeCodeBtn.addEventListener('click', () => {
//    // Get input from the code editor
//    const userCode = editor.getValue();
//
//    // Run the user code
//    try {
//        new Function(userCode)();
//    } catch (err) {
//        console.error(err);
//    }
//});
//
//resetCodeBtn.addEventListener('click', () => {
//    // Clear ace editor
//    editor.setValue(defaultCode);
//})

