function acroread($file) {
   $currdir=get-location
   $filepath=$currdir.ProviderPath+"\"+$file
   start "C:\Program Files (x86)\Adobe\Acrobat DC\Acrobat\Acrobat.exe" $filepath
}

