!define APP_NAME "Interview"
!define COMP_NAME "Donato Rimenti"
!define MODULE_NAME "Interview"
!define WEB_SITE "https://www.aurasphere.co"
!define VERSION "01.00.00.00"
!define COPYRIGHT "©2015 Donato Rimenti"
!define DESCRIPTION "Interview application"
!define INSTALLER_NAME ".\${APP_NAME} Installer.exe"
!define MAIN_APP_EXE "${APP_NAME}.exe"
!define INSTALL_TYPE "SetShellVarContext current"
!define REG_ROOT "HKCU"
!define REG_APP_PATH "Software\Microsoft\Windows\CurrentVersion\App Paths\${MAIN_APP_EXE}"
!define UNINSTALL_PATH "Software\Microsoft\Windows\CurrentVersion\Uninstall\${APP_NAME}"

######################################################################

VIProductVersion  "${VERSION}"
VIAddVersionKey "ProductName"  "${APP_NAME}"
VIAddVersionKey "CompanyName"  "${COMP_NAME}"
VIAddVersionKey "LegalCopyright"  "${COPYRIGHT}"
VIAddVersionKey "FileDescription"  "${DESCRIPTION}"
VIAddVersionKey "FileVersion"  "${VERSION}"

######################################################################

SetCompressor ZLIB
Name "${APP_NAME}"
Caption "${APP_NAME}"
OutFile "${INSTALLER_NAME}"
BrandingText "${APP_NAME}"
XPStyle on
InstallDirRegKey "${REG_ROOT}" "${REG_APP_PATH}" ""
InstallDir "$PROGRAMFILES\${COMP_NAME}\${MODULE_NAME}"

######################################################################

# Icons

!include "MUI2.nsh"

!define MUI_ICON "..\..\src\main\resources\icon.ico"
!define MUI_HEADERIMAGE
!define MUI_HEADERIMAGE_BITMAP "..\..\src\main\resources\splash.png"
!define MUI_HEADERIMAGE_RIGHT

###############################################‡‡‡####################

!include "MUI.nsh"

!define MUI_ABORTWARNING
!define MUI_UNABORTWARNING

!define MUI_LANGDLL_REGISTRY_ROOT "${REG_ROOT}"
!define MUI_LANGDLL_REGISTRY_KEY "${UNINSTALL_PATH}"
!define MUI_LANGDLL_REGISTRY_VALUENAME "Installer Language"

!insertmacro MUI_PAGE_WELCOME

!ifdef LICENSE_TXT
!insertmacro MUI_PAGE_LICENSE "${LICENSE_TXT}"
!endif

!ifdef REG_START_MENU
!define MUI_STARTMENUPAGE_NODISABLE
!define MUI_STARTMENUPAGE_DEFAULTFOLDER "${COMP_NAME}\${MODULE_NAME}"
!define MUI_STARTMENUPAGE_REGISTRY_ROOT "${REG_ROOT}"
!define MUI_STARTMENUPAGE_REGISTRY_KEY "${UNINSTALL_PATH}"
!define MUI_STARTMENUPAGE_REGISTRY_VALUENAME "${REG_START_MENU}"
!insertmacro MUI_PAGE_STARTMENU Application $SM_Folder
!endif

!insertmacro MUI_PAGE_INSTFILES

!insertmacro MUI_PAGE_FINISH

!insertmacro MUI_UNPAGE_CONFIRM

!insertmacro MUI_UNPAGE_INSTFILES

!insertmacro MUI_UNPAGE_FINISH

!insertmacro MUI_LANGUAGE "English"
!insertmacro MUI_LANGUAGE "Italian"

!insertmacro MUI_RESERVEFILE_LANGDLL

######################################################################

Function .onInit
!insertmacro MUI_LANGDLL_DISPLAY
FunctionEnd

######################################################################

Section -MainProgram
${INSTALL_TYPE}
SetOverwrite ifnewer
SetOutPath "$INSTDIR"
File "..\..\target\${MAIN_APP_EXE}"
File "..\..\target\interview-desktop.jar"
File "..\..\..\interview-server\target\interview-server.war"
File "chromedriver.exe"
File "desktop-config.properties"
CreateDirectory "$APPDATA\${COMP_NAME}\${MODULE_NAME}\data"
CreateDirectory "$APPDATA\${COMP_NAME}\${MODULE_NAME}\logs"
CreateDirectory "$APPDATA\${COMP_NAME}\${MODULE_NAME}\logs\database"
SectionEnd

######################################################################

# Install and configuration of Java and Mongo if needed

!include "EnvVarUpdate.nsh"
!include "servicelib.nsh" 
Section -Prerequisites
IfFileExists $PROGRAMFILES\Java\jre1.8.0_151 endJavaInstallation startJavaInstallation
startJavaInstallation:
	File "jre-8u151-windows-i586.exe"
	ExecWait '"$INSTDIR\jre-8u151-windows-i586.exe"'
	Delete "$INSTDIR\jre-8u151-windows-i586.exe"
endJavaInstallation:
IfFileExists $PROGRAMFILES64\MongoDB\Server\3.4 endMongoInstallation startMongoInstallation
Goto endMongoInstallation
startMongoInstallation:
	File "mongodb-win32-x86_64-2008plus-ssl-3.4.10-signed.msi"
	ExecWait '"msiexec" /i "$INSTDIR\mongodb-win32-x86_64-2008plus-ssl-3.4.10-signed.msi"'  
	Delete "$INSTDIR\mongodb-win32-x86_64-2008plus-ssl-3.4.10-signed.msi"
	${EnvVarUpdate} $0 "PATH" "A" "HKLM" "$PROGRAMFILES64\MongoDB\Server\3.4\bin"
endMongoInstallation:
File "..\..\..\interview-server\assets\scripts\mongo-database-creation.js"
File "..\..\..\interview-server\assets\scripts\init-db.cmd"
ExecWait '"init-db.cmd"'
Delete "mongo-database-creation.js"
Delete "init-db.cmd"
!insertmacro SERVICE create "Interview MongoDB" 'path=$PROGRAMFILES64\MongoDB\Server\3.4\bin\mongod.exe --service --auth --dbpath="$APPDATA\Aurasphere\Interview\data" --logpath="$APPDATA\Aurasphere\Interview\logs\database\interview-mongo.log";autostart=1;display=Interview MongoDB'
!insertmacro SERVICE start "Interview MongoDB" ""
SectionEnd
  
######################################################################

Section -Icons_Reg
SetOutPath "$INSTDIR"
WriteUninstaller "$INSTDIR\uninstall.exe"

!ifdef REG_START_MENU
!insertmacro MUI_STARTMENU_WRITE_BEGIN Application
CreateDirectory "$SMPROGRAMS\$SM_Folder"
CreateShortCut "$SMPROGRAMS\$SM_Folder\${APP_NAME}.lnk" "$INSTDIR\${MAIN_APP_EXE}"
CreateShortCut "$DESKTOP\${APP_NAME}.lnk" "$INSTDIR\${MAIN_APP_EXE}"
CreateShortCut "$SMPROGRAMS\$SM_Folder\Uninstall ${APP_NAME}.lnk" "$INSTDIR\uninstall.exe"

!ifdef WEB_SITE
WriteIniStr "$INSTDIR\${APP_NAME} website.url" "InternetShortcut" "URL" "${WEB_SITE}"
CreateShortCut "$SMPROGRAMS\$SM_Folder\${APP_NAME} Website.lnk" "$INSTDIR\${APP_NAME} website.url"
!endif
!insertmacro MUI_STARTMENU_WRITE_END
!endif

!ifndef REG_START_MENU
CreateDirectory "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}"
CreateShortCut "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}\${APP_NAME}.lnk" "$INSTDIR\${MAIN_APP_EXE}"
CreateShortCut "$DESKTOP\${APP_NAME}.lnk" "$INSTDIR\${MAIN_APP_EXE}"
CreateShortCut "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}\Uninstall ${APP_NAME}.lnk" "$INSTDIR\uninstall.exe"

!ifdef WEB_SITE
WriteIniStr "$INSTDIR\${APP_NAME} website.url" "InternetShortcut" "URL" "${WEB_SITE}"
CreateShortCut "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}\${APP_NAME} Website.lnk" "$INSTDIR\${APP_NAME} website.url"
!endif
!endif

WriteRegStr ${REG_ROOT} "${REG_APP_PATH}" "" "$INSTDIR\${MAIN_APP_EXE}"
WriteRegStr ${REG_ROOT} "${UNINSTALL_PATH}"  "DisplayName" "${APP_NAME}"
WriteRegStr ${REG_ROOT} "${UNINSTALL_PATH}"  "UninstallString" "$INSTDIR\uninstall.exe"
WriteRegStr ${REG_ROOT} "${UNINSTALL_PATH}"  "DisplayIcon" "$INSTDIR\${MAIN_APP_EXE}"
WriteRegStr ${REG_ROOT} "${UNINSTALL_PATH}"  "DisplayVersion" "${VERSION}"
WriteRegStr ${REG_ROOT} "${UNINSTALL_PATH}"  "Publisher" "${COMP_NAME}"

!ifdef WEB_SITE
WriteRegStr ${REG_ROOT} "${UNINSTALL_PATH}"  "URLInfoAbout" "${WEB_SITE}"
!endif
SectionEnd

######################################################################

Section Uninstall
${INSTALL_TYPE}
Delete "$INSTDIR\${MAIN_APP_EXE}"
Delete "$INSTDIR\interview-server.war"
Delete "$INSTDIR\chromedriver.exe"
Delete "$INSTDIR\desktop-config.properties"
Delete "$INSTDIR\interview-desktop.jar"
 
Delete "$INSTDIR\uninstall.exe"
!ifdef WEB_SITE
Delete "$INSTDIR\${APP_NAME} website.url"
!endif

RmDir "$INSTDIR"

!ifdef REG_START_MENU
!insertmacro MUI_STARTMENU_GETFOLDER "Application" $SM_Folder
Delete "$SMPROGRAMS\$SM_Folder\${APP_NAME}.lnk"
Delete "$SMPROGRAMS\$SM_Folder\Uninstall ${APP_NAME}.lnk"
!ifdef WEB_SITE
Delete "$SMPROGRAMS\$SM_Folder\${APP_NAME} Website.lnk"
!endif
Delete "$DESKTOP\${APP_NAME}.lnk"

RmDir "$SMPROGRAMS\$SM_Folder"
!endif

!ifndef REG_START_MENU
Delete "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}\${APP_NAME}.lnk"
Delete "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}\Uninstall ${APP_NAME}.lnk"
!ifdef WEB_SITE
Delete "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}\${APP_NAME} Website.lnk"
!endif
Delete "$DESKTOP\${APP_NAME}.lnk"

RmDir "$SMPROGRAMS\${COMP_NAME}\${MODULE_NAME}"
RmDir "$SMPROGRAMS\${COMP_NAME}"
!endif

DeleteRegKey ${REG_ROOT} "${REG_APP_PATH}"
DeleteRegKey ${REG_ROOT} "${UNINSTALL_PATH}"

!insertmacro SERVICE stop "Interview MongoDB" ""
!insertmacro SERVICE delete "Interview MongoDB" ""
SectionEnd

######################################################################

Function un.onInit
!insertmacro MUI_UNGETLANGUAGE
FunctionEnd

######################################################################